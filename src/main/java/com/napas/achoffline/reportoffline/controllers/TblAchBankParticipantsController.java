/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.models.TblAchBankParticipantsDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblAchBankParticipantsService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author huynx
 */
@RestController
@RequestMapping("/api/TblAchBankParticipants")
public class TblAchBankParticipantsController {

    @Autowired
    private TblAchBankParticipantsService tblAchBankParticipantsService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping()

    public List<TblAchBankParticipantsDTO> list() {
        return tblAchBankParticipantsService.list();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

}
