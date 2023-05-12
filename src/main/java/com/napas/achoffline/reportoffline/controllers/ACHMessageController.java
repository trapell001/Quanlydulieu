package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.models.AchInputMessagesDTO;
import com.napas.achoffline.reportoffline.models.AchOutputMessagesDTO;
import com.napas.achoffline.reportoffline.service.AchMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/achmessage")
public class ACHMessageController {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private AchMessageService achMsgSvc;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

    @GetMapping("/input/{reference}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD')")
    public List<AchInputMessagesDTO> listInputMessagesByMsgID(@PathVariable("reference") String msgID) {
        return achMsgSvc.listInputMessagesByMsgID(msgID);
    }

    @GetMapping("/output/{reference}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD')")
    public List<AchOutputMessagesDTO> listOutputMessagesByMsgID(@PathVariable("reference") String msgID) {
        return achMsgSvc.listOutputMessagesByMsgID(msgID);
    }

    @GetMapping("/output/{txid}/{reference}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD')")
    public List<AchOutputMessagesDTO> listOutputMessagesDispute(@PathVariable("txid") String txid,
                                                                @PathVariable("reference") String reference) {
        return achMsgSvc.listOutputMessagesDispute(txid, reference);
    }

//    @GetMapping(path = "/output/{msgID}", produces = "application/json; charset=UTF-8")
//    @PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'OPERATOR', 'TRA_SOAT', 'KIEM_SOAT', 'RD', 'SENIOR_RD')")
//    public List<ACHOutputMessage> getOutputMessagesByMsgID(@PathVariable("msgID") String msgID) {
//        return achMsgSvc.getOutputMessagesByMsgID(msgID);
//    }
}
