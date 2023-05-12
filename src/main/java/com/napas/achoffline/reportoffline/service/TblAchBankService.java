/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblAchBank;
import com.napas.achoffline.reportoffline.models.TblAchBankDTO;
import com.napas.achoffline.reportoffline.repository.TblAchBankDAO;
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
public class TblAchBankService {

    @Autowired
    private TblAchBankDAO tblAchBankDAO;

    @Autowired
    private ModelMapper mapper;

    private TblAchBankDTO fromEntity(TblAchBank entity) {
        TblAchBankDTO dto = mapper.map(entity, TblAchBankDTO.class);
        return dto;
    }

    public List<TblAchBankDTO> list() {
        return tblAchBankDAO.findAll(Sort.by("bicCodeSwift"))
                .stream()
                .filter(p -> !p.getBankCode().contains("NAPAS"))
                .map(p -> fromEntity(p)).collect(Collectors.toList());
    }
}
