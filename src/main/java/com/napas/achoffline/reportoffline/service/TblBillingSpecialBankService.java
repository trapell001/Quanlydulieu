/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblBillingSpecialBank;
import com.napas.achoffline.reportoffline.models.TblBillingSpecialBankDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblBillingSpecialBankDAO;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author huynx
 */
@Service
public class TblBillingSpecialBankService {

    @Autowired
    private TblBillingSpecialBankDAO billingSpecialBankDAO;

    @Autowired
    private ModelMapper mapper;

    private TblBillingSpecialBankDTO fromEntity(TblBillingSpecialBank entity) {
        TblBillingSpecialBankDTO dto = mapper.map(entity, TblBillingSpecialBankDTO.class);
        return dto;
    }

    private TblBillingSpecialBank fromDTO(TblBillingSpecialBankDTO dto) {
        TblBillingSpecialBank entity = mapper.map(dto, TblBillingSpecialBank.class);
        return entity;
    }

    public List<TblBillingSpecialBankDTO> list() {
        List<TblBillingSpecialBank> dbResult = billingSpecialBankDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblBillingSpecialBankDTO get(int id) {
        return fromEntity(billingSpecialBankDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblBillingSpecialBankDTO input) {
        TblBillingSpecialBank entity = fromDTO(input);
        entity.setId(id);

        TblBillingSpecialBank currentEntity = billingSpecialBankDAO.findById(id).orElse(null);
        entity.setDateCreadted(currentEntity.getDateCreadted());

        entity = billingSpecialBankDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> post(TblBillingSpecialBankDTO input) {
        TblBillingSpecialBank entity = fromDTO(input);
        entity.setDateCreadted(new Date());
        entity = billingSpecialBankDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> delete(int id) {
        billingSpecialBankDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }
}
