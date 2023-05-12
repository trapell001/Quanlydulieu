package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.service.HisBatchInstrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/batchinstr")
@Slf4j
public class HisBatchInstrController {

    @Autowired
    private HisBatchInstrService hisBatchInstrService;

    @GetMapping("/searchpaging")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> findBatchPayments(@RequestParam(name = "aDocId", required = false) Long aDocId,
                                               @RequestParam(name = "begin", required = false) String dateBegin,
                                               @RequestParam(name = "end", required = false) String dateEnd,
                                               @RequestParam(name = "txid", required = false) String txid,
                                               @RequestParam(name = "transStatus", required = false) String status,
                                               @RequestParam(name = "viewStatus", required = false) String viewStatus,
                                               @RequestParam(name = "debtorName", required = false) String debtorName,
                                               @RequestParam(name = "debtorType", required = false) String debtorType,
                                               @RequestParam(name = "debtorAccount", required = false) String debtorAccount,
                                               @RequestParam(name = "creditorName", required = false) String creditorName,
                                               @RequestParam(name = "creditorType", required = false) String creditorType,
                                               @RequestParam(name = "creditorAccount", required = false) String creditorAccount,
                                               @RequestParam(name = "rtpFlag", required = false) String rtp,
                                               @RequestParam("pageIndex") Integer pageIndex,
                                               @RequestParam("pagesize") Integer pagesize) {
        return hisBatchInstrService.search(dateBegin, dateEnd, aDocId, txid, status, viewStatus, debtorName,
                debtorType, debtorAccount, creditorName, creditorType, creditorAccount, rtp, pageIndex, pagesize);
    }

    @GetMapping("/{aDocId}/{pageIndex}/{pagesize}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> findByADocId(@PathVariable("aDocId") Long aDocId,
                                          @PathVariable("pageIndex") Integer pageIndex,
                                          @PathVariable("pagesize") Integer pagesize
    ) {
        return hisBatchInstrService.findByADocId(aDocId, pageIndex, pagesize);
    }

    @GetMapping("/export/excel")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public void exportToExcel(HttpServletResponse response,
                              @RequestParam(name = "begin", required = false) String dateBegin,
                              @RequestParam(name = "end", required = false) String dateEnd,
                              @RequestParam(name = "aDocId", required = false) Long aDocId,
                              @RequestParam(name = "txid", required = false) String txid,
                              @RequestParam(name = "transStatus", required = false) String status,
                              @RequestParam(name = "viewStatus", required = false) String viewStatus,
                              @RequestParam(name = "debtorName", required = false) String debtorName,
                              @RequestParam(name = "debtorType", required = false) String debtorType,
                              @RequestParam(name = "debtorAccount", required = false) String debtorAccount,
                              @RequestParam(name = "creditorName", required = false) String creditorName,
                              @RequestParam(name = "creditorType", required = false) String creditorType,
                              @RequestParam(name = "creditorAccount", required = false) String creditorAccount,
                              @RequestParam(name = "rtpFlag", required = false) String rtp) throws IOException {
        hisBatchInstrService.exportToExcel(response,dateBegin,dateEnd,aDocId, txid, status, viewStatus,
                debtorName, debtorType, debtorAccount, creditorName, creditorType, creditorAccount, rtp);
    }
}
