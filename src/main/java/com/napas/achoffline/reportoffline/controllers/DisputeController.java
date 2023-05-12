package com.napas.achoffline.reportoffline.controllers;


import com.napas.achoffline.reportoffline.define.FundTransferSystem;
import com.napas.achoffline.reportoffline.service.DisputeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dispute")
@Slf4j
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    @GetMapping(path = "/searchpaging")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public ResponseEntity<?> getDisputeList(
            @RequestParam(name = "beginCreate", required = false) String beginCreate,
            @RequestParam(name = "endCreate", required = false) String endCreate,
            @RequestParam(name = "beginModif", required = false) String beginModif,
            @RequestParam(name = "endModif", required = false) String endModif,
            @RequestParam(name = "beginResponse", required = false) String beginResponse,
            @RequestParam(name = "endResponse", required = false) String endResponse,
            @RequestParam(name = "reference", required = false) String reference,
            @RequestParam(name = "claimant", required = false) String claimant,
            @RequestParam(name = "claimee", required = false) String claimee,
            @RequestParam(name = "respondent", required = false) String respondent,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "dispCat", required = false) String dispCat,
            @RequestParam(name = "dispBatchReference", required = false) String dispBatchReference,
            @RequestParam(name = "dispTxid", required = false) String dispTxid,
            @RequestParam(name = "page", required = false) String pageIndex,
            @RequestParam(name = "pagesize", required = false) String pageSize
    ) throws ParseException, Exception {
        return disputeService.getDisputeList(beginCreate,endCreate,beginModif,endModif,beginResponse,endResponse,
                reference,claimant,claimee, respondent,status,dispCat,dispBatchReference,dispTxid,
                Integer.parseInt(pageIndex) - 1, Integer.parseInt(pageSize));
    }

    @GetMapping("/export/excel")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD', 'BUSINESS', 'SENIOR_BUSINESS')")
    public void exportToExcel(HttpServletResponse response,
                              @RequestParam(name = "beginCreate", required = false) String beginCreate,
                              @RequestParam(name = "endCreate", required = false) String endCreate,
                              @RequestParam(name = "beginModif", required = false) String beginModif,
                              @RequestParam(name = "endModif", required = false) String endModif,
                              @RequestParam(name = "beginResponse", required = false) String beginResponse,
                              @RequestParam(name = "endResponse", required = false) String endResponse,
                              @RequestParam(name = "reference", required = false) String reference,
                              @RequestParam(name = "claimant", required = false) String claimant,
                              @RequestParam(name = "claimee", required = false) String claimee,
                              @RequestParam(name = "respondent", required = false) String respondent,
                              @RequestParam(name = "status", required = false) String status,
                              @RequestParam(name = "dispCat", required = false) String dispCat,
                              @RequestParam(name = "dispBatchReference", required = false) String dispBatchReference,
                              @RequestParam(name = "dispTxid", required = false) String dispTxid) throws IOException {
        disputeService.exportDispute(response,beginCreate,endCreate, beginModif,endModif,beginResponse,endResponse,
                reference,claimant,claimee, respondent,status,dispCat,dispBatchReference,dispTxid);
    }
}
