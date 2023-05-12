/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.define.FundTransferSystem;
import com.napas.achoffline.reportoffline.entity.Payments;
import com.napas.achoffline.reportoffline.models.PaymentFilter;
import com.napas.achoffline.reportoffline.models.PaymentsDTO;
import com.napas.achoffline.reportoffline.models.PaymentsExport;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.PaymentExportDAO;
import com.napas.achoffline.reportoffline.repository.PaymentsDAO;
import com.napas.achoffline.reportoffline.sup.PaymentsExcelExporter;
import com.napas.achoffline.reportoffline.utils.ParticipantCoupleUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author HuyNX
 */
@Service
public class PaymentsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private PaymentsDAO paymentsDAO;

    @Autowired
    private PaymentExportDAO paymentsExportDAO;


    @Autowired
    private ModelMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${napas.ach.offline.export.payment-export-dir}")
    private String paymentExportDir;

    @Value("${napas.ach.offline.search-payment.max-days}")
    private long maxDays;

    @Value("${napas.ach.offline.export.max-rows-for-xlsx}")
    private long maxRowsForXlsx;

    static DateTimeFormatter recordformatter = DateTimeFormatter.ofPattern("HH:mm:ss YYYY-MM-dd");

    private static String dataFormat = "%d;%s;%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n";

    public ResponseEntity<?> findByTxid(String txid) {
        Payments entity = paymentsDAO.findByTxid(txid);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(fromEntity(entity, false));
    }

    private PaymentsDTO fromEntity(Payments entity, boolean fullAccountNumberView) {
        PaymentsDTO dto = mapper.map(entity, PaymentsDTO.class);

        if (!fullAccountNumberView) {
            dto.setDebtorAccount(dto.getDebtorAccount().replaceAll(".", "x"));
            dto.setCreditorAccount(dto.getCreditorAccount().replaceAll(".", "x"));
        }
        return dto;
    }

    public ResponseEntity<?> searchPaging(
            String beginDate,
            String endDate,
            String sessionId,
            String toSessionId,
            String msgId,
            String mxType,
            String transStatus,
            String authInfo,
            String minAmount,
            String maxAmount,
            String debitBank,
            String creditBank,
            String debtorName,
            String creditorName,
            String debtorAccount,
            String creditorAccount,
            String debtorAccountType,
            String creditorAccountType,
            String channel,
            String boc,
            String ttc,
            String msgToCreditor,
            String debtorSystem,
            String creditorSystem,
            String rtp,
            int page,
            int pageSize,
            boolean fullAccountNumberView
    ) {
        if (!fullAccountNumberView
                && ((debtorAccount != null && debtorAccount.length() > 0)
                || (creditorAccount != null && creditorAccount.length() > 0))) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Tài khoản không đủ quyền truy cập số thẻ/số tài khoản"));
        }

        SimpleDateFormat searchDateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date beginDateSearch;
        Date endDateSearch;

        try {
            if(beginDate.length() == 16) {
                beginDate = beginDate + ":00";
            }

            if(endDate.length() == 16) {
                endDate = endDate + ":00";
            }

            beginDateSearch = searchDateFmt.parse(beginDate);
            endDateSearch = searchDateFmt.parse(endDate);

            if (endDateSearch.getTime() - beginDateSearch.getTime() >= maxDays * 86400L * 1000L) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Không thể tra cứu quá 31 ngày"));
            }

            Pageable sortedById = null;

            Sort sort = Sort.by("A_DOC_ID").descending();

            if (pageSize == -1) {
                sortedById = Pageable.unpaged();
            } else {
                sortedById = PageRequest.of(page, pageSize, sort);
            }

            //Fund System
//            ParticipantCoupleUtil.ParticipantCouple participantSearch = ParticipantCoupleUtil.
//                    calculateParticipantCouple(debitBank, debtorSystem, creditBank, creditorSystem);

            Page<Payments> listPayments = paymentsDAO.searchPayments(
                    sortedById,
                    beginDateSearch,
                    endDateSearch,
                    sessionId,
                    toSessionId,
                    mxType,
                    msgId,
                    transStatus,
                    authInfo,
                    minAmount,
                    maxAmount,
                    debitBank,
                    creditBank,
                    debtorName,
                    creditorName,
                    debtorAccount,
                    creditorAccount,
                    debtorAccountType,
                    creditorAccountType,
                    channel,
                    boc,
                    ttc,
                    msgToCreditor,
                    convert(debtorSystem),
                    convert(creditorSystem),
                    rtp
            );

//            Page<Payments> listPayments = paymentsDAO.searchPaymentsSimple(sortedById, beginDateSearch, endDateSearch, msgId, deptorName);

            Page<PaymentsDTO> output = listPayments.map(entity -> fromEntity(entity, fullAccountNumberView));

            return ResponseEntity.ok(output);
        } catch (Exception ex) {
            logger.error("Loi:" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Lỗi truy vấn"));
        }
    }

    public String convert(String s) {
        if(s.equals("ACH")) return "VNVN";
        if(s.equals("IBFT")) return "VNVP";
        return null;
    }


    public void exportPayment(
        HttpServletResponse response,
        LocalDateTime beginDate,
        LocalDateTime endDate,
        Integer sessionId,
        Integer toSessionId,
        String msgId,
        String mxType,
        String transStatus,
        String authInfo,
        BigDecimal minAmount,
        BigDecimal maxAmount,
        String debitBank,
        String creditBank,
        String debtorName,
        String creditorName,
        String debtorAccount,
        String creditorAccount,
        String debtorAccountType,
        String creditorAccountType,
        String channel,
        String boc,
        String ttc,
        String msgToCreditor,
        String debtorSystem,
        String creditorSystem,
        String rtp,
        Boolean covidOnly
    ) throws IOException {
        //Fund System
//        ParticipantCoupleUtil.ParticipantCouple participantSearch = ParticipantCoupleUtil.calculateParticipantCouple(debitBank, debtorSystem, creditBank, creditorSystem);

        PaymentFilter f = new PaymentFilter(
            beginDate,
            endDate,
            sessionId,
            toSessionId,
            mxType,
            msgId,
            transStatus,
            authInfo,
            minAmount,
            maxAmount,
            debitBank,
            creditBank,
            debtorName,
            creditorName,
            debtorAccount,
            creditorAccount,
            debtorAccountType,
            creditorAccountType,
            channel,
            boc,
            ttc,
            msgToCreditor,
            covidOnly,
            convert(debtorSystem),
            convert(creditorSystem),
            rtp
        );

        long paymentCount = paymentsExportDAO.countPayment(f);
        logger.info("{} giao dich thoa man dieu kien:{}", paymentCount, f.toString());

        if (paymentCount <= maxRowsForXlsx) {
            long beginExportTime = System.currentTimeMillis();
            List<PaymentsExport> listPayments = paymentsExportDAO.searchAllPayments(f, false, 0);
            PaymentsExcelExporter excelExporter = new PaymentsExcelExporter(listPayments, paymentExportDir);
            excelExporter.export(response);
            logger.info("Xuat {} giao dich trong {}ms", paymentCount, System.currentTimeMillis() - beginExportTime);
        } else {
            long beginExportTime = System.currentTimeMillis();
            exportCsv(response, f);
            logger.info("-EXPORT- Xuat giao dich trong {}ms", System.currentTimeMillis() - beginExportTime);
        }
    }

    public void exportCsv(HttpServletResponse response,
                          PaymentFilter f
    ) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileName = String.format("Payment_%s.csv", currentDateTime);
        String filePath = paymentExportDir + "/" + fileName;

        String headerLine = "Accept Date;Trans Date;Request Num;Status;Authorise;Amount;Debtor agent;Creditor agent;Msg Type;Session ID;Error code;Refusal code;TTC;Channel ID\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(headerLine);

            int pageIndex = 0;
            int dataIndex = 1;
            List<PaymentsExport> listData = null;
            while (true) {
                long beginExportTime = System.currentTimeMillis();
                listData = paymentsExportDAO.searchAllPayments(f, true, pageIndex);
                if (listData.size() == 0) {
                    break;
                }
                for (PaymentsExport p : listData) {
                    String dataLine = createDataLine(dataIndex, p);
                    writer.append(dataLine);
                    dataIndex++;
                }
                writer.flush();
                pageIndex++;
                logger.info("-EXPORT_LOOP- Xuat {} giao dich trong {}ms", listData.size(), System.currentTimeMillis() - beginExportTime);
            }
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

    private String createDataLine(int dataIndex, PaymentsExport p) {
        StringBuilder dataLines = new StringBuilder();
        dataLines.append(p.processDate().format(recordformatter));
        dataLines.append(";");
        dataLines.append(p.transDate().format(recordformatter));
        dataLines.append(";");
        dataLines.append(p.txid());
        dataLines.append(";");
        dataLines.append(p.transStatus());
        dataLines.append(";");
        dataLines.append(p.authInfo());
        dataLines.append(";");
        dataLines.append(String.valueOf(p.settlementAmount()));
        dataLines.append(";");
        dataLines.append(p.instructingAgent());
        dataLines.append(";");
        dataLines.append(p.instructedAgent());
        dataLines.append(";");
        dataLines.append(p.type());
        dataLines.append(";");
        dataLines.append(p.sessionId());
        dataLines.append(";");
        dataLines.append(p.achResultCode());
        dataLines.append(";");
        dataLines.append(p.partyResultCode());
        dataLines.append(";");
        dataLines.append(p.ttc());
        dataLines.append(";");
        dataLines.append(p.channelId());
        dataLines.append("\n");

        return dataLines.toString();
    }


}


