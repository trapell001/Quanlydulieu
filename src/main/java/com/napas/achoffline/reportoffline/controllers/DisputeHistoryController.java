package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.repository.DisputeHistoryDAO;
import com.napas.achoffline.reportoffline.service.DisputeHistoryService;
import com.napas.achoffline.reportoffline.service.DisputeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/history")
@Slf4j
public class DisputeHistoryController {
    @Autowired
    private DisputeHistoryService disputeHistoryService;

    @GetMapping(path = "/dispute/searchbyreference")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public ResponseEntity<?> listDisputeHistory(@RequestParam(name = "reference") String reference) {
        return disputeHistoryService.getDisputeHistory(reference);
    }

    @GetMapping(path = "/payments/searchbyreference")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public ResponseEntity<?> listPaymentHistory(@RequestParam(name = "reference") String reference) {
        return disputeHistoryService.getPaymentsHistory(reference);
    }
}
