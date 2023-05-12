/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.models.PaymentFilterAcspNaut;
import com.napas.achoffline.reportoffline.models.PaymentsExportAcspNaut;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.PaymentAcspDAO;
import com.napas.achoffline.reportoffline.repository.PaymentExportAcspNautDAO;
import com.napas.achoffline.reportoffline.support.PaymentsExcelExporterAcspNaut;
import lombok.extern.slf4j.Slf4j;
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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HuyNX
 */
@Service
@Slf4j
public class PaymentsAcspNautService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private PaymentAcspDAO paymentAcspDAO;
    @Autowired
    private PaymentExportAcspNautDAO paymentsExportDAO;

    @Value("${napas.ach.offline.export.payment-export-dir}")
    private String paymentExportDir;

    @Value("${napas.ach.offline.search-payment.max-days}")
    private long maxDays;

    @Value("${napas.ach.offline.export.max-rows-for-xlsx}")
    private long maxRowsForXlsx;

    static DateTimeFormatter recordformatter = DateTimeFormatter.ofPattern("HH:mm:ss YYYY-MM-dd");



    private static String dataFormat = "%d;%s;%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n";


    public void exportPaymentAcspNaut(
            HttpServletResponse response,
            LocalDateTime beginDate,
            LocalDateTime endDate,
            String mxType,
            String participant,
            String haveReturn
    ) throws IOException {
        //Fund System

        PaymentFilterAcspNaut f = new PaymentFilterAcspNaut(
                beginDate,
                endDate,
                mxType,
                participant,
                haveReturn
        );

        long paymentCount = paymentsExportDAO.countPayment(f);
        logger.info("{} giao dich thoa man dieu kien:{}", paymentCount, f.toString());

        if (paymentCount <= maxRowsForXlsx) {
            long beginExportTime = System.currentTimeMillis();
            List<PaymentsExportAcspNaut> listPayments = paymentsExportDAO.searchAllPaymentsAcspNaut(f, false, 0);
            PaymentsExcelExporterAcspNaut paymentsExcelExporterAcspNaut= new PaymentsExcelExporterAcspNaut(listPayments, paymentExportDir);
            paymentsExcelExporterAcspNaut.export(response);
            logger.info("Xuat {} giao dich trong {}ms", paymentCount, System.currentTimeMillis() - beginExportTime);
        } else {
            long beginExportTime = System.currentTimeMillis();
            exportCsv(response, f);
            logger.info("-EXPORT- Xuat giao dich trong {}ms", System.currentTimeMillis() - beginExportTime);
        }
    }

public ResponseEntity<?> searchPagingAcspNaut(
        LocalDateTime beginDate,
        LocalDateTime endDate,
        String mxType,
        String haveReturn,
        String participant,
        int page,
        int pageSize,
        boolean fullAccountNumberView
) {
    PaymentFilterAcspNaut f = new PaymentFilterAcspNaut(
            beginDate,
            endDate,
            mxType,
            participant,
            haveReturn
    );


    try {
        Pageable sortedById = null;

        Sort sort = Sort.by("A_PROCESS_DATE").descending();

        if (pageSize == -1) {
            sortedById = Pageable.unpaged();
        } else {
            sortedById = PageRequest.of(page, pageSize, sort);
        }

        Page<PaymentsExportAcspNaut> listPayments = paymentAcspDAO.searchAllPaymentsAcspNaut(f,sortedById);
        System.out.println("list"+ listPayments.toString());

        return ResponseEntity.ok(listPayments);
    } catch (Exception ex) {
        logger.error("Loi:" + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Lỗi truy vấn"));
    }
}
    public void exportCsv(HttpServletResponse response,
                          PaymentFilterAcspNaut f
    ) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileName = String.format("PaymentACSPNAUT_%s.csv", currentDateTime);
        String filePath = paymentExportDir + "/" + fileName;

        String headerLine = "Accept Date;Trans Date;Request Num;Status;Authorise;Amount;Debtor agent;Creditor agent;Msg Type;Session Id;Error code;Refusal code\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.append(headerLine);

            int pageIndex = 0;
            int dataIndex = 1;
            List<PaymentsExportAcspNaut> listData = null;
            while (true) {
                long beginExportTime = System.currentTimeMillis();
                listData = paymentsExportDAO.searchAllPaymentsAcspNaut(f, true, pageIndex);
                if (listData.size() == 0) {
                    break;
                }
                for (PaymentsExportAcspNaut p : listData) {
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
    // sửa adpend lại
    private String createDataLine(int dataIndex, PaymentsExportAcspNaut p) {
        StringBuilder dataLines = new StringBuilder();
        dataLines.append(p.beginDate());
        dataLines.append(";");
        dataLines.append(p.endDate());
        dataLines.append(";");
        dataLines.append(p.msgId());
        dataLines.append(";");
        dataLines.append(p.transStatus());
        dataLines.append(";");
        dataLines.append(p.authInfo());
        dataLines.append(";");
        dataLines.append(p.amount());
        dataLines.append(";");
        dataLines.append(p.debtorAgent());
        dataLines.append(";");
        dataLines.append(p.creditorAgent());
        dataLines.append(";");
        dataLines.append(p.mxType());
        dataLines.append(";");
        dataLines.append(p.sessionId());
        dataLines.append(";");
        dataLines.append(p.txid004());
        dataLines.append(";");
        dataLines.append(p.transStatus004());
        dataLines.append(";");
        dataLines.append(p.amount004());
        dataLines.append(";");
        dataLines.append(p.endDate004());
        dataLines.append(";");
        dataLines.append(p.sessionId004());
        dataLines.append(";");
        dataLines.append("\n");

        return dataLines.toString();
    }


}
