/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.define.FundTransferSystem;
import com.napas.achoffline.reportoffline.service.PaymentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * @author HuyNX
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private PaymentsService paymentsService;

    @GetMapping(path = "/searchbytxid")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public ResponseEntity<?> findByTxid(
        @RequestParam(name = "txid") String txid
    ) {
        return paymentsService.findByTxid(txid);
    }

    @GetMapping(path = "/searchpaging")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public ResponseEntity<?> getPaymentsList(
            @RequestParam(name = "begin") String beginDate,
            @RequestParam(name = "end") String endDate,
            @RequestParam(name = "sessionid", required = false) String sessionId,
            @RequestParam(name = "tosessionid", required = false) String toSessionId,
            @RequestParam(name = "msgid", required = false) String msgId,
            @RequestParam(name = "mxtype", required = false) String mxType,
            @RequestParam(name = "transstatus", required = false) String transStatus,
            @RequestParam(name = "authinfo", required = false) String authInfo,
            @RequestParam(name = "minamount", required = false) String minAmount,
            @RequestParam(name = "maxamount", required = false) String maxAmount,
            @RequestParam(name = "debitbank", required = false) String debitBank,
            @RequestParam(name = "creditbank", required = false) String creditBank,
            @RequestParam(name = "debtorname", required = false) String debtorName,
            @RequestParam(name = "creditorname", required = false) String creditorName,
            @RequestParam(name = "debtoraccount", required = false) String debtorAccount,
            @RequestParam(name = "creditoraccount", required = false) String creditorAccount,
            @RequestParam(name = "debtoraccounttype", required = false) String debtorAccountType,
            @RequestParam(name = "creditoraccounttype", required = false) String creditorAccountType,
            @RequestParam(name = "channel", required = false) String channel,
            @RequestParam(name = "boc", required = false) String boc,
            @RequestParam(name = "ttc", required = false) String ttc,
            @RequestParam(name = "msg", required = false) String msgToCreditor,
            @RequestParam(name = "debtorSystem", required = false) String debtorSystem,
            @RequestParam(name = "creditorSystem", required = false) String creditorSystem,
            @RequestParam(name = "rtpFlag", required = false) String rtp,
            @RequestParam(name = "page", required = false) String pageIndex,
            @RequestParam(name = "pagesize", required = false) String pageSize
    ) throws ParseException, Exception {
        //Bổ sung cái này để kiểm soát quyền tra cứu theo số tài khoản
        Boolean fullAccountNumberView = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("KIEM_SOAT")
                || a.getAuthority().equals("TRA_SOAT")
        )) {
            fullAccountNumberView = true;
        }

        return paymentsService.searchPaging(beginDate, endDate, sessionId, toSessionId, msgId, mxType,
                transStatus, authInfo,
                minAmount, maxAmount,
                debitBank, creditBank, debtorName, creditorName,
                debtorAccount, creditorAccount, debtorAccountType, creditorAccountType,
                channel, boc, ttc, msgToCreditor,
                debtorSystem, creditorSystem, rtp,
                Integer.parseInt(pageIndex) - 1, Integer.parseInt(pageSize),
                fullAccountNumberView);
    }

    @GetMapping("/export/excel")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    public void exportToExcel(
        HttpServletResponse response,
        @RequestParam(name = "begin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beginDate,
        @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
        @RequestParam(name = "sessionid", required = false) Integer sessionId,
        @RequestParam(name = "tosessionid", required = false) Integer toSessionId,
        @RequestParam(name = "msgid", required = false) String msgId,
        @RequestParam(name = "mxtype", required = false) String mxType,
        @RequestParam(name = "transstatus", required = false) String transStatus,
        @RequestParam(name = "authinfo", required = false) String authInfo,
        @RequestParam(name = "minamount", required = false) BigDecimal minAmount,
        @RequestParam(name = "maxamount", required = false) BigDecimal maxAmount,
        @RequestParam(name = "debitbank", required = false) String debitBank,
        @RequestParam(name = "creditbank", required = false) String creditBank,
        @RequestParam(name = "debtorname", required = false) String debtorName,
        @RequestParam(name = "creditorname", required = false) String creditorName,
        @RequestParam(name = "debtoraccount", required = false) String debtorAccount,
        @RequestParam(name = "creditoraccount", required = false) String creditorAccount,
        @RequestParam(name = "debtoraccounttype", required = false) String debtorAccountType,
        @RequestParam(name = "creditoraccounttype", required = false) String creditorAccountType,
        @RequestParam(name = "channel", required = false) String channel,
        @RequestParam(name = "boc", required = false) String boc,
        @RequestParam(name = "ttc", required = false) String ttc,
        @RequestParam(name = "msg", required = false) String msgToCreditor,
        @RequestParam(name = "debtorSystem", required = false) String debtorSystem,
        @RequestParam(name = "creditorSystem", required = false) String creditorSystem,
        @RequestParam(name = "rtpFlag", required = false) String rtp,
        @RequestParam(name = "covidOnly", required = false) Boolean covidOnly
    ) throws IOException {
        //Bổ sung cái này để kiểm soát quyền tra cứu theo số tài khoản
        Boolean fullAccountNumberView = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("KIEM_SOAT"))) {
            fullAccountNumberView = true;
        }

        paymentsService.exportPayment(
            response,
            beginDate,
            endDate,
            sessionId,
            toSessionId,
            msgId,
            mxType,
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
            channel, boc, ttc, msgToCreditor,
            debtorSystem, creditorSystem, rtp,
            covidOnly);
    }


}
