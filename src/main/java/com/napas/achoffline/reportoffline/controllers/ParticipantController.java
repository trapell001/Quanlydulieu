package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.entity.Participant;
import com.napas.achoffline.reportoffline.models.TblAchBankParticipantsDTO;
import com.napas.achoffline.reportoffline.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping()
    public List<Participant> list() {
        return participantService.list();
    }
}
