package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.PaymentsAcspNautService;
import com.napas.achoffline.reportoffline.utils.LogApiAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 *
 * @author HuyNX
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/paymentsAcspNaut")
public class PaymentsAcspNautController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private PaymentsAcspNautService paymentsAcspNautService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

    @GetMapping(path = "/searchpaging")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public ResponseEntity<?> getPaymentsListAcspNaut(
            @RequestParam(name = "begin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginDate,
            @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(name = "mxtype", required = false) String mxType,
            @RequestParam(name = "page", required = false) String pageIndex,
            @RequestParam(name = "pagesize", required = false) String pageSize,
            @RequestParam(name = "havereturn", required = false) String haveReturn,
            @RequestParam(name ="participant",required = false) String participant,
            HttpServletRequest request
    ){
        //Bổ sung cái này để kiểm soát quyền tra cứu theo số tài khoản
        Boolean fullAccountNumberView = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("KIEM_SOAT")
                || a.getAuthority().equals("TRA_SOAT")
        )) {
            fullAccountNumberView = true;
        }
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        return paymentsAcspNautService.searchPagingAcspNaut(beginDate, endDate,
                mxType,haveReturn,participant,
                Integer.parseInt(pageIndex) - 1, Integer.parseInt(pageSize),
                fullAccountNumberView
        );
    }


    @GetMapping("/export/excel")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    public void exportToExcel(
            HttpServletResponse response,
            @RequestParam(name = "begin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginDate,
            @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(name ="participant",required = false) String participant,
            @RequestParam(name = "mxtype", required = false) String mxType,
            @RequestParam(name = "havereturn", required = false) String haveReturn,
            HttpServletRequest request


    ) throws IOException {
        //Bổ sung cái này để kiểm soát quyền tra cứu theo số tài khoản
        Boolean fullAccountNumberView = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("KIEM_SOAT"))) {
            fullAccountNumberView = true;
        }
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        paymentsAcspNautService.exportPaymentAcspNaut(
                response,
                beginDate,
                endDate,
                mxType,
                participant,
                haveReturn
        );
    }



}