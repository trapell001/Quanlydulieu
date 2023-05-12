package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.service.HisRtpTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/statisticrtp")
public class RptTransactionStatisticController {

    @Autowired
    private HisRtpTransactionService hisRtpTransactionService;

    @GetMapping(path = "/export/excel")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public void export(HttpServletResponse response,
                       @RequestParam(name = "begin", required = false) String beginDate,
                       @RequestParam(name = "end", required = false) String endDate,
                       @RequestParam(name = "reportType") String reportType,
                       @RequestParam(name = "boc") String boc,
                       @RequestParam(name = "ttc") String ttc,
                       @RequestParam(name = "rtpType", required = false) String rtpType,
                       @RequestParam(name = "initiatorAgent", required = false) String initiatorAgent,
                       @RequestParam(name = "debtorAgent", required = false) String debtorAgent,
                       @RequestParam(name = "creditorAgent", required = false) String creditorAgent,
                       @RequestParam(name = "transStatus", required = false) String transStatus,
                       @RequestParam(name = "creditorSystem", required = false) String initiatingSystem) {

        response.setContentType("application/x-download");
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());

        String fileName = String.format("RtpStatistic_%s_to_%s_export_at_%s.xlsx", beginDate, endDate, currentDateTime);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        response.setHeader("X-Suggested-Filename", fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        hisRtpTransactionService.exportStatistic(response, beginDate, endDate, reportType, boc, ttc,
                rtpType, initiatorAgent, debtorAgent, creditorAgent, transStatus, initiatingSystem);
    }
}
