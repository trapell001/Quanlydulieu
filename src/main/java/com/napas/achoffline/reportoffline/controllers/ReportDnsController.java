package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.define.ReportOfflineExportFileType;
import com.napas.achoffline.reportoffline.models.*;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.ReportDnsService;
import com.napas.achoffline.reportoffline.service.ReportOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reportdns")
public class ReportDnsController {
    @Autowired
    private ReportDnsService reportDnsService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/export/{reportCode}/{format}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public void exportReport(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable String reportCode,
                             @PathVariable ReportOfflineExportFileType format,
                             ReportDnsFilter filter
    ) throws IOException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        response.setContentType("application/x-download");
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());

        String fileName = String.format("%s_%s.%s", reportCode.toString(), currentDateTime, format);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        response.setHeader("X-Suggested-Filename", fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        OutputStream out = response.getOutputStream();
        reportDnsService.generateReport(
                reportCode,
                filter,
                format,
                out);
    }

    @GetMapping("/list")
    public List<ReportDnsDefDTO> listAllReportOffline() {
        return reportDnsService.listAllReportDns();
    }

    @GetMapping("/listTransType")
    public List<TblRptoHeaderLevel1DTO> listTransType() {
        return reportDnsService.listAllReportOfflineTransType();
    }
}
