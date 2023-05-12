package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.service.BatchPaymentsStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/statisticbatchpayments")
public class BatchPaymentsStatisticController {

    @Autowired
    private BatchPaymentsStatisticService batchPaymentsStatisticService;


    @GetMapping("/export/excel")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public void exportBatchPayments(
            HttpServletResponse response,
            @RequestParam(name = "beginDate") String beginDate,
            @RequestParam(name = "endDate") String endDate,
            @RequestParam(name = "reportType") String reportType,
            @RequestParam(name = "boc") String boc,
            @RequestParam(name = "ttc") String ttc,
            @RequestParam(name = "transType") String transType,
            @RequestParam(name = "creditorType") String creditorType,
            @RequestParam(name = "transStatus") String transStatus,
            @RequestParam(name = "debtorAgent") String instructingAgent
    ) throws ParseException, IOException {
        response.setContentType("application/x-download");
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());

        String fileName = String.format("BatchPaymentsStatistic_%s_to_%s_export_at_%s.xlsx", beginDate, endDate, currentDateTime);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        response.setHeader("X-Suggested-Filename", fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        batchPaymentsStatisticService.getBatchPaymentExcelStream(response, beginDate, endDate, reportType, boc, ttc, transType,
                creditorType, transStatus, instructingAgent);
    }
}
