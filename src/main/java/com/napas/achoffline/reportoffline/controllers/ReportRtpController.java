package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.define.ReportOfflineExportFileType;
import com.napas.achoffline.reportoffline.models.ReportOfflineDefDTO;
import com.napas.achoffline.reportoffline.models.ReportOfflineFilter;
import com.napas.achoffline.reportoffline.models.ReportRtpDefDTO;
import com.napas.achoffline.reportoffline.models.ReportRtpFilter;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.ReportOfflineService;
import com.napas.achoffline.reportoffline.service.ReportRtpService;
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
@RequestMapping("/api/reportrtp")
public class ReportRtpController {

    @Autowired
    private ReportRtpService reportRtpService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/export/{reportCode}/{format}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public void exportReport(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable String reportCode,
                             @PathVariable ReportOfflineExportFileType format,
                             ReportRtpFilter filter
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
        reportRtpService.generateReport(
                reportCode,
                filter,
                format,
                out);
    }

    @GetMapping("/list")
    public List<ReportRtpDefDTO> listAllReportOffline() {
        return reportRtpService.listAllReportRtp();
    }
}
