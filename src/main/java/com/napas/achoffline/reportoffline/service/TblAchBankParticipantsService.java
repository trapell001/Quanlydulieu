/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblAchBank;
import com.napas.achoffline.reportoffline.entity.TblAchBankParticipants;
import com.napas.achoffline.reportoffline.models.TblAchBankDTO;
import com.napas.achoffline.reportoffline.models.TblAchBankParticipantsDTO;
import com.napas.achoffline.reportoffline.repository.TblAchBankParticipantsDAO;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author huynx
 */
@Service
public class TblAchBankParticipantsService {

    @Autowired
    private TblAchBankParticipantsDAO tblAchBankParticipantsDAO;

    @Autowired
    private ModelMapper mapper;

    private TblAchBankParticipantsDTO fromEntity(TblAchBankParticipants entity) {
        TblAchBankParticipantsDTO dto = mapper.map(entity, TblAchBankParticipantsDTO.class);

        TblAchBank bankInfo = entity.getAchBankId();
        TblAchBankDTO bankDto = mapper.map(bankInfo, TblAchBankDTO.class);

        dto.setAchBank(bankDto);

        return dto;
    }

    public List<TblAchBankParticipantsDTO> list() {
        return tblAchBankParticipantsDAO.findAll(Sort.by("bicCode"))
                .stream()
                .filter(p -> !p.getTypeId().contentEquals("0"))
                .map(p -> fromEntity(p)).collect(Collectors.toList());
    }
}
