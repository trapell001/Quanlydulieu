package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.service.HisRtpTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rtp")
public class HisRtpTransactionController {

    @Autowired
    private HisRtpTransactionService hisRtpTransactionService;

    @GetMapping(path = "/searchpaging")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> searchpaging(HttpServletRequest request,
                                          @RequestParam(name = "begin", required = false) String beginDate,
                                          @RequestParam(name = "end", required = false) String endDate,
                                          @RequestParam(name = "txid", required = false) String txid,
                                          @RequestParam(name = "msgIdRtp", required = false) String msgIdRtp,
                                          @RequestParam(name = "rtpType", required = false) String rtpType,
                                          @RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
                                          @RequestParam(name = "sessionTo", required = false) Long sessionTo,
                                          @RequestParam(name = "transStatus", required = false) String transStatus,
                                          @RequestParam(name = "boc", required = false) String boc,
                                          @RequestParam(name = "initiatorAgent", required = false) String initiatorAgent,
                                          @RequestParam(name = "debtorAgent", required = false) String debtorAgent,
                                          @RequestParam(name = "creditorAgent", required = false) String creditorAgent,
                                          @RequestParam(name = "debtorName", required = false) String debtorName,
                                          @RequestParam(name = "debtorAccount", required = false) String debtorAccount,
                                          @RequestParam(name = "creditorName", required = false) String creditorName,
                                          @RequestParam(name = "creditorAccount", required = false) String creditorAccount,
                                          @RequestParam(name = "creditorSystem", required = false) String initiatingSystem,
                                          @RequestParam(name = "page", required = false) Integer pageIndex,
                                          @RequestParam(name = "pagesize", required = false) Integer pagesize

    ) {
        return hisRtpTransactionService.searchpaging(beginDate, endDate,txid, msgIdRtp, rtpType, sessionFrom, sessionTo,
                transStatus, boc, initiatorAgent, debtorAgent, creditorAgent, debtorName, debtorAccount,
                creditorName, creditorAccount, initiatingSystem, pageIndex, pagesize);
    }

    @GetMapping(path = "/export/excel")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public void export(HttpServletResponse response,
                       @RequestParam(name = "begin", required = false) String beginDate,
                       @RequestParam(name = "end", required = false) String endDate,
                       @RequestParam(name = "txid", required = false) String txid,
                       @RequestParam(name = "msgIdRtp", required = false) String msgIdRtp,
                       @RequestParam(name = "rtpType", required = false) String rtpType,
                       @RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
                       @RequestParam(name = "sessionTo", required = false) Long sessionTo,
                       @RequestParam(name = "transStatus", required = false) String transStatus,
                       @RequestParam(name = "boc", required = false) String boc,
                       @RequestParam(name = "initiatorAgent", required = false) String initiatorAgent,
                       @RequestParam(name = "debtorAgent", required = false) String debtorAgent,
                       @RequestParam(name = "creditorAgent", required = false) String creditorAgent,
                       @RequestParam(name = "debtorName", required = false) String debtorName,
                       @RequestParam(name = "debtorAccount", required = false) String debtorAccount,
                       @RequestParam(name = "creditorName", required = false) String creditorName,
                       @RequestParam(name = "creditorAccount", required = false) String creditorAccount,
                       @RequestParam(name = "creditorSystem", required = false) String initiatingSystem

    ) {
        hisRtpTransactionService.export(response,beginDate, endDate, txid, msgIdRtp, rtpType, sessionFrom, sessionTo,
                transStatus, boc, initiatorAgent, debtorAgent, creditorAgent, debtorName,
                debtorAccount, creditorName, creditorAccount, initiatingSystem);
    }
}
