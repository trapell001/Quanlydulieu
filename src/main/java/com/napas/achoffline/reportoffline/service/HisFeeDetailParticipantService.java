package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.HisFeeDetailParticipant;
import com.napas.achoffline.reportoffline.models.HisFeeDetailParticipantDTO;
import com.napas.achoffline.reportoffline.repository.HisFeeDetailParticipantDAO;
import com.napas.achoffline.reportoffline.utils.CalculateS;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HisFeeDetailParticipantService {
    @Autowired
    private HisFeeDetailParticipantDAO hisFeeDetailParticipantDAO;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> searchHisFeeDetailParticipant(Long sessionFrom, Long sessionTo, String participantBic) {
        List<HisFeeDetailParticipant> list = hisFeeDetailParticipantDAO.
                searchHisFeeDetailParticipant(sessionFrom,sessionTo,participantBic);
        List<HisFeeDetailParticipantDTO> newList = new ArrayList<>();
        for(HisFeeDetailParticipant item : list) {
            Long temp = (Long) (CalculateS.calculateDay(item.getDateBegin(),
                    item.getDateEnd()) * 100000);
            if(item.getSlgdTt().compareTo(0L) != 0) {
                temp = (Long) (temp / item.getSlgdTt());
                newList.add(new HisFeeDetailParticipantDTO(temp, item.getSessionId(), "TCTV"));
            }
        }
        newList = newList.stream().sorted(Comparator.comparingLong(HisFeeDetailParticipantDTO::getSession))
                .collect(Collectors.toList());
        return ResponseEntity.ok(newList);
    }
}
