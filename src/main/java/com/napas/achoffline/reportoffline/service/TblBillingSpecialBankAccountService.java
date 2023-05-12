/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblBillingSpecialBankAccount;
import com.napas.achoffline.reportoffline.models.TblBillingSpecialBankAccountDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblBillingSpecialBankAccountDAO;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author huynx
 */
@Service
public class TblBillingSpecialBankAccountService {

    @Autowired
    private TblBillingSpecialBankAccountDAO billingSpecialBankAccountDAO;

    @Autowired
    private ModelMapper mapper;

    private ResponseEntity<?> validateData(TblBillingSpecialBankAccountDTO input) {
        if (input.getValidFrom() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Không thể để trống thời gian bắt đầu hiệu lực"));
        }

        if (input.getValidTo() != null && input.getValidTo().before(input.getValidFrom())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Thời gian kết thúc hiệu lực nhỏ hơn thời gian bắt đầu hiệu lực"));
        }

        if (input.getValidFrom().before(new Date())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Thời gian bắt đầu hiệu lực không được nhỏ hơn ngày hiện tại"));
        }

        return ResponseEntity.ok(new MessageResponse("OK"));
    }

    private TblBillingSpecialBankAccountDTO fromEntity(TblBillingSpecialBankAccount entity) {
        TblBillingSpecialBankAccountDTO dto = mapper.map(entity, TblBillingSpecialBankAccountDTO.class);
        return dto;
    }

    private TblBillingSpecialBankAccount fromDTO(TblBillingSpecialBankAccountDTO dto) {
        TblBillingSpecialBankAccount entity = mapper.map(dto, TblBillingSpecialBankAccount.class);
        return entity;
    }

    public List<TblBillingSpecialBankAccountDTO> list() {
        List<TblBillingSpecialBankAccount> dbResult = billingSpecialBankAccountDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblBillingSpecialBankAccountDTO get(int id) {
        return fromEntity(billingSpecialBankAccountDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblBillingSpecialBankAccountDTO input) {
        try {
            ResponseEntity<?> validateDataStatus = validateData(input);
            if (validateDataStatus.getStatusCode() != HttpStatus.OK) {
                return validateDataStatus;
            }
            TblBillingSpecialBankAccount entity = billingSpecialBankAccountDAO.findById(id).orElse(null);

            if(entity == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new MessageResponse("Tài khoản không tồn tại"));
            }
            entity.setValidTo(input.getValidTo());
            entity.setValidFrom(input.getValidFrom());
            entity.setCampaignId(input.getCampaignId());
            entity.setAccountNumber(input.getAccountNumber());
            entity.setBic(input.getBic());
            entity.setDirection(input.getDirection());
            entity.setDescription(input.getDescription());
            billingSpecialBankAccountDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Tài khoản đã tồn tại"));
        }
    }

    public ResponseEntity<?> post(TblBillingSpecialBankAccountDTO input) {
        try {
            ResponseEntity<?> validateDataStatus = validateData(input);
            if (validateDataStatus.getStatusCode() != HttpStatus.OK) {
                return validateDataStatus;
            }
            TblBillingSpecialBankAccount entity = fromDTO(input);
            entity.setDateCreadted(new Date());
            entity.setCampaignId(input.getCampaignId());
            entity = billingSpecialBankAccountDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Tài khoản đã tồn tại"));
        }
    }
    public ResponseEntity<?> delete(int id) {
        billingSpecialBankAccountDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }
}
