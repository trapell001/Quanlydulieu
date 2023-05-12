/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.define.RptBillingSearchType;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.HisNrtPaymentsFeeService;
import java.io.IOException;
import java.security.Principal;
import java.time.YearMonth;

import com.napas.achoffline.reportoffline.utils.LogApiAccess;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author huynx
 */
@RestController
@RequestMapping("/api/HisNrtPaymentsFee")
public class HisNrtPaymentsFeeController {

    @Autowired
    private HisNrtPaymentsFeeService hisNrtPaymentsFeeService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping("/export/csv")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT')")
    public void exportToCsv(HttpServletRequest request,
            HttpServletResponse response,
                            @RequestParam(name = "bic", required = true) String bic,
                            @RequestParam(name = "searchType") RptBillingSearchType searchType,
                            @RequestParam(name = "month", required = false) YearMonth month,
                            @RequestParam(name = "sessionFrom", required = false) Long sessionFromId,
                            @RequestParam(name = "sessionTo", required = false) Long sessionToId
    ) throws IOException, Exception {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        hisNrtPaymentsFeeService.exportCsv(response, bic, searchType, month, sessionFromId, sessionToId);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

}
