/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.define.CdtDbtInd;
import com.napas.achoffline.reportoffline.define.RptBillingSearchType;
import com.napas.achoffline.reportoffline.entity.HisNrtPaymentsFee;
import com.napas.achoffline.reportoffline.models.HisNrtPaymentsFeeDTO;
import com.napas.achoffline.reportoffline.models.SessionRange;
import com.napas.achoffline.reportoffline.models.TblSessions;
import com.napas.achoffline.reportoffline.repository.HisNrtPaymentsFeeDAO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author huynx
 */
@Service
public class HisNrtPaymentsFeeService {

    private static final int PAGE_SIZE = 1000;

    @Value("${napas.ach.offline.export.payment-export-dir}")
    private String paymentExportDir;

    @Autowired
    private HisNrtPaymentsFeeDAO hisNrtPaymentsFeeDTO;

    @Autowired
    private AchSessionService achSessionsService;

    @Autowired
    private ModelMapper mapper;

    private DateFormat valueDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static String dataFormat = "%d;\t%s;%d;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s;\t%s\n";

    private HisNrtPaymentsFeeDTO fromEntity(HisNrtPaymentsFee entity) {
        HisNrtPaymentsFeeDTO dto = mapper.map(entity, HisNrtPaymentsFeeDTO.class);
        return dto;
    }

    public Page<HisNrtPaymentsFeeDTO> listPaging(String bic, CdtDbtInd cdtDbtInd, TblSessions sessionFrom, TblSessions sessionTo, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex, PAGE_SIZE, Sort.by("docId").ascending());

        Date processDateFrom = Date
                .from(sessionFrom.getStartTime().truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault())
                        .toInstant());

        Date processDateTo = Date
                .from(sessionTo.getFinishTime().truncatedTo(ChronoUnit.DAYS).plusDays(1).minusSeconds(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

        long sessionFromId = sessionFrom.getId();
        long sessionToId = sessionTo.getId();

        Page<HisNrtPaymentsFee> listData = null;
        if (cdtDbtInd == CdtDbtInd.DBIT) {
            listData = hisNrtPaymentsFeeDTO.findByDebtorAgentAndSessionIdBetweenAndProcessDateBetween(paging, bic, sessionFromId, sessionToId, processDateFrom, processDateTo);
        } else {
            listData = hisNrtPaymentsFeeDTO.findByCreditorAgentAndSessionIdBetweenAndProcessDateBetween(paging, bic, sessionFromId, sessionToId, processDateFrom, processDateTo);
        }

        Page<HisNrtPaymentsFeeDTO> output = listData.map(entity -> fromEntity(entity));

        return output;
    }

    public void exportCsv(HttpServletResponse response,
            String bic, RptBillingSearchType searchType, YearMonth month, Long sessionFromId, Long sessionToId
    ) throws IOException {
        TblSessions sessionFrom;
        TblSessions sessionTo;
        if (searchType == RptBillingSearchType.SESSION_RANGE) {
            sessionFrom = achSessionsService.getTblSessionById(sessionFromId);
            sessionTo = achSessionsService.getTblSessionById(sessionToId);
        } else {
            SessionRange sessionRange = achSessionsService.getMonthsessionRangeByMonth(month);
            sessionFrom = sessionRange.getBeginSession();
            sessionTo = sessionRange.getEndSession();
        }

        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileName = String.format("PaymentFee_%d-%d_%s.csv", sessionFrom.getId(), sessionTo.getId(), currentDateTime);
        String filePath = paymentExportDir + "/" + fileName;

        String headerLine = "NtryRef;MonthOrder;AcctSvcrRef;Acct ID;Ownr ID;MsgId;OrigMsgId;InstrId;EndToEndId;TxId;Amt;CdtDbtInd;RltdPties;ValDt;BkTxCd;TTC;Channel;Add Payment;Special Account;FEE_IRF_ISS;FEE_SVF_ISS;FEE_IRF_RCV;FEE_SVF_RCV\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(headerLine);

            int pageIndex = 0;
            int dataIndex = 1;
            Page<HisNrtPaymentsFeeDTO> listData = null;
            do {
                listData = listPaging(bic, CdtDbtInd.DBIT, sessionFrom, sessionTo, pageIndex);
                for (HisNrtPaymentsFeeDTO p : listData.getContent()) {
                    String dataLine = createDataLine(bic, CdtDbtInd.DBIT, dataIndex, p);
                    writer.append(dataLine);
                    dataIndex++;
                }
                writer.flush();
                pageIndex++;
            } while (listData != null && !listData.isLast());

            pageIndex = 0;
            do {
                listData = listPaging(bic, CdtDbtInd.CRDT, sessionFrom, sessionTo, pageIndex);
                for (HisNrtPaymentsFeeDTO p : listData.getContent()) {
                    String dataLine = createDataLine(bic, CdtDbtInd.CRDT, dataIndex, p);
                    writer.append(dataLine);
                    dataIndex++;
                }
                writer.flush();
                pageIndex++;
            } while (listData != null && !listData.isLast());
        }

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("X-Suggested-Filename", fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        File file = new File(filePath);

        // Content-Length
        response.setContentLength((int) file.length());

        try (BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
                BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream())) {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
        }
    }

    private String createDataLine(String bic, CdtDbtInd cdtDbtInd, int dataIndex, HisNrtPaymentsFeeDTO p) {
        String dataLine = String.format(dataFormat,
                dataIndex,
                cdtDbtInd == CdtDbtInd.DBIT ? p.getMonthOrder().toString() : "",
                p.getSessionId(),
                bic,
                bic,
                p.getTxid() != null ? p.getTxid() : "",
                p.getOriginalTxid() != null ? p.getOriginalTxid() : "",
                p.getInstrId() != null ? p.getInstrId() : "",
                p.getEndToEndId() != null ? p.getEndToEndId() : "",
                p.getTxid() != null ? p.getTxid() : "",
                p.getAmount(),
                cdtDbtInd.toString(),
                p.getDebtorAgent(),
                valueDateFormat.format(p.getValueDate()),
                p.getMsgType(),
                p.getTtc(),
                p.getChannelId() != null ? p.getChannelId() : "",
                p.getAddedPaymentFlag() == 1 ? "yes" : "",
                p.getSpecialBankAccountFlag() == 1 ? "yes" : "",
                p.getFeeIrfIss().setScale(0, RoundingMode.HALF_UP),
                p.getFeeSvfIss().setScale(0, RoundingMode.HALF_UP),
                p.getFeeIrfRcv().setScale(0, RoundingMode.HALF_UP),
                p.getFeeSvfRcv().setScale(0, RoundingMode.HALF_UP)
        );

        return dataLine;
    }

}
