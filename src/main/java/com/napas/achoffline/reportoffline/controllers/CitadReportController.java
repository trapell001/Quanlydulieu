/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.define.ReportOfflineExportFileType;
import com.napas.achoffline.reportoffline.entity.TblUserToken;
import com.napas.achoffline.reportoffline.models.ReportOfflineFilter;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblUserTokenRepository;
import com.napas.achoffline.reportoffline.service.CitadReportService;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.ReportOfflineService;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author huynx
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/CitadReport")
public class CitadReportController {

    @Autowired
    private ReportOfflineService reportOfflineService;

    @Autowired
    private CitadReportService citadReportService;

    @Autowired
    private TblUserTokenRepository tblUserTokenRepository;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/exportToFile/{sessionId}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public void exportReport(HttpServletResponse response,
            @PathVariable String sessionId
    ) throws IOException {
        response.setContentType("application/x-download");
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormatter.format(new Date());

        String reportCode = "RO_06_DEFAULT";
        ReportOfflineExportFileType format = ReportOfflineExportFileType.XLSX;
        ReportOfflineFilter filter = new ReportOfflineFilter();
        filter.setSessionBegin(sessionId);
        filter.setSessionEnd(sessionId);

        String fileName = String.format("%s_%s.%s", reportCode, currentDateTime, format.toString());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        response.setHeader("X-Suggested-Filename", fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        OutputStream out = response.getOutputStream();
        reportOfflineService.generateReport(
                reportCode,
                filter,
                format,
                out);
    }

    @PostMapping("/exportToCitad/{sessionId}")
    @PreAuthorize("hasAnyAuthority('TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> exportToCitad(HttpServletRequest request,@PathVariable String sessionId) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return citadReportService.exportToCitad(sessionId);
    }
    
    @GetMapping("/checkSettleDay/{sessionType}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> checkSettleDay(@PathVariable Long sessionType) {
        return citadReportService.checkSettleDay(sessionType);
    }
    
    @PostMapping("/settleCurrentDay")
    @PreAuthorize("hasAnyAuthority('TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> settleCurrentDay(HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "", "");
        return citadReportService.settleCurrentDay();
    }
}
