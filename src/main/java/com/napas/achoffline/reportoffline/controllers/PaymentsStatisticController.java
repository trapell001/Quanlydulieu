package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.service.PaymentsStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/statisticnrt")
public class PaymentsStatisticController {

    @Autowired
    private PaymentsStatisticService paymentsStatisticService;

    @GetMapping("/export/excel")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public void exportPaymentAggForCLDV(
            HttpServletResponse response,
            @RequestParam(name = "beginDate") String beginDate,
            @RequestParam(name = "endDate") String endDate,
            @RequestParam(name = "reportType") String reportType,
            @RequestParam(name = "boc") String boc,
            @RequestParam(name = "ttc") String ttc,
            @RequestParam(name = "transType") String transType,
            @RequestParam(name = "creditorType") String creditorType,
            @RequestParam(name = "status") String status,
            @RequestParam(name = "auth") String auth,
            @RequestParam(name = "channel") String channel,
            @RequestParam(name = "debtorAgent") String instructingAgent,
            @RequestParam(name = "creditorAgent") String instructedAgent,
            @RequestParam(name = "debtorSystem", required = false) String debtorSystem,
            @RequestParam(name = "creditorSystem", required = false) String creditorSystem
    ) throws ParseException, IOException {
        response.setContentType("application/x-download");
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());

        String fileName = String.format("PaymentsStatistic_%s_to_%s_export_at_%s.xlsx", beginDate, endDate, currentDateTime);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        response.setHeader("X-Suggested-Filename", fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        paymentsStatisticService.getPaymentExcelStream(response, beginDate, endDate, reportType, boc, ttc, transType,
                creditorType, status, auth, channel, instructingAgent, instructedAgent, debtorSystem, creditorSystem);
    }
}
