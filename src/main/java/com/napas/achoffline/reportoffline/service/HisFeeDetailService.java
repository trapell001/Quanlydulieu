package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.HisFeeDetail;
import com.napas.achoffline.reportoffline.models.HisFeeDetailDTO;
import com.napas.achoffline.reportoffline.repository.HisFeeDetailDAO;
import com.napas.achoffline.reportoffline.utils.CalculateS;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HisFeeDetailService {
    @Autowired
    private HisFeeDetailDAO hisFeeDetailDAO;

    @Autowired
    private ModelMapper modelMapper;
    public ResponseEntity<?> searchHisFeeDetail(Long sessionFrom, Long sessionTo) {
        List<HisFeeDetail> list = hisFeeDetailDAO.searchHisFeeDetail(sessionFrom,sessionTo);
        List<HisFeeDetailDTO> newList = new ArrayList<>();
        for(HisFeeDetail item : list) {
            Long temp = (Long) (CalculateS.calculateDay(item.getDateBegin(),
                    item.getDateEnd()) * 100000);
            if(item.getSlgdTT().compareTo(0L) != 0) {
                temp = (Long) (temp / item.getSlgdTT());
                newList.add(new HisFeeDetailDTO(temp, item.getSessionId()));
            }
        }
        newList = newList.stream().sorted(Comparator.comparingLong(HisFeeDetailDTO::getSession)).collect(Collectors.toList());
        return ResponseEntity.ok(newList);
    }
}
