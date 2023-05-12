package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.service.HisBatchPaymentsService;
import com.napas.achoffline.reportoffline.utils.LogApiAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/api/batchpayments")
@Slf4j
public class HisBatchPaymentsController {

    @Autowired
    private HisBatchPaymentsService hisBatchPaymentsService;

    @GetMapping("/searchpaging")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> getBatchPayments(
            @RequestParam(name = "begin", required = false) String dateBegin,
            @RequestParam(name = "end", required = false) String dateEnd,
            @RequestParam(name = "msgId", required = false) String msgId,
            @RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
            @RequestParam(name = "sessionTo", required = false) Long sessionTo,
            @RequestParam(name = "transStatus", required = false) String status,
            @RequestParam(name = "viewStatus", required = false) String viewStatus,
            @RequestParam(name = "msgType", required = false) String msgType,
            @RequestParam(name = "boc", required = false) String boc,
            @RequestParam(name = "ttc", required = false) String ttc,
            @RequestParam(name = "debtorAgent", required = false) String tcpl,
            @RequestParam(name = "creditorAgent", required = false) String tcnl,
            @RequestParam(name = "creditorType", required = false) String creditorType,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "pagesize", required = false) Integer pagesize

    ) {
        return hisBatchPaymentsService.searchBatchPayments(dateBegin, dateEnd, msgId, sessionFrom,
                sessionTo, status, viewStatus, msgType, boc, ttc, tcpl, tcnl, creditorType, page, pagesize);
    }

    @GetMapping("/export/excel")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public void exportToExcel(HttpServletResponse response,
                              @RequestParam(name = "begin", required = false) String dateBegin,
                              @RequestParam(name = "end", required = false) String dateEnd,
                              @RequestParam(name = "msgId", required = false) String msgId,
                              @RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
                              @RequestParam(name = "sessionTo", required = false) Long sessionTo,
                              @RequestParam(name = "transStatus", required = false) String status,
                              @RequestParam(name = "viewStatus", required = false) String viewStatus,
                              @RequestParam(name = "msgType", required = false) String msgType,
                              @RequestParam(name = "boc", required = false) String boc,
                              @RequestParam(name = "ttc", required = false) String ttc,
                              @RequestParam(name = "debtorAgent", required = false) String tcpl,
                              @RequestParam(name = "creditorAgent", required = false) String tcnl,
                              @RequestParam(name = "creditorType", required = false) String creditorType
    ) {
        hisBatchPaymentsService.exportExcel(response,dateBegin, dateEnd, msgId, sessionFrom,
                sessionTo, status, viewStatus, msgType, boc, ttc, tcpl, tcnl, creditorType);
    }
}
