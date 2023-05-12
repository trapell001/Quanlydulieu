package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.Participant;
import com.napas.achoffline.reportoffline.models.TblAchBankParticipantsDTO;
import com.napas.achoffline.reportoffline.repository.ParticipantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantDAO participantDAO;

    public List<Participant> list() {
        return participantDAO.findAll(Sort.by("participantCode"))
                .stream()
                .map(p -> new Participant(p.getParticipantId(),
                        p.getParticipantCode().substring(0,p.getParticipantCode().length() - 4),
                        p.getName())).collect(Collectors.toList());

    }
}
