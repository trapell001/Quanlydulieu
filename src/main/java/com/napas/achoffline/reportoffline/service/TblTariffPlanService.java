/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblTariffPlan;
import com.napas.achoffline.reportoffline.models.TblTariffPlanDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblTariffLadderDAO;
import com.napas.achoffline.reportoffline.repository.TblTariffPlanDAO;
import com.napas.achoffline.reportoffline.repository.TblTariffPlanFreeChannelDAO;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.napas.achoffline.reportoffline.repository.TblTariffPlanParticipantDAO;
import com.napas.achoffline.reportoffline.repository.TblTariffWithoutLadderDAO;

/**
 * @author huynx
 */
@Service
public class TblTariffPlanService {

    //private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Autowired
    private TblTariffPlanDAO tariffPlanDAO;

    @Autowired
    private TblTariffLadderDAO tariffLadderDAO;

    @Autowired
    private TblTariffWithoutLadderDAO tariffWithoutLadderDAO;

    @Autowired
    private TblTariffPlanParticipantDAO tariffPlanParticipantDAO;

    @Autowired
    private TblTariffPlanFreeChannelDAO tariffPlanFreeChannelDAO;

    @Autowired
    private ModelMapper mapper;

    private TblTariffPlanDTO fromEntity(TblTariffPlan entity) {
        TblTariffPlanDTO dto = mapper.map(entity, TblTariffPlanDTO.class);
        return dto;
    }

    private TblTariffPlan fromDTO(TblTariffPlanDTO dto) {
        TblTariffPlan entity = mapper.map(dto, TblTariffPlan.class);
        return entity;
    }

    public List<TblTariffPlanDTO> list() {
        List<TblTariffPlan> dbResult = tariffPlanDAO.searchAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblTariffPlanDTO get(int id) {
        return fromEntity(tariffPlanDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblTariffPlanDTO input) {

        try {
            TblTariffPlan entity = tariffPlanDAO.findById(id).orElse(null);
            if(entity == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                        body(new MessageResponse("Biểu phí không tồn tại"));
            }
            entity.setDateModified(new Date());
            entity.setPlanName(input.getPlanName());
            entity.setPlanDescription(input.getPlanDescription());
            entity.setLadderEnabledAmount(input.getLadderEnabledAmount());
            entity.setReturnPaymentFeeType(input.getReturnPaymentFeeType());
            entity.setTariffPlanCode(input.getTariffPlanCode().trim());
            entity = tariffPlanDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Mã biểu phí đã tồn tại"));
        }

    }

    public ResponseEntity<?> post(TblTariffPlanDTO input) {
        try {
            TblTariffPlan entity = fromDTO(input);
            entity.setDateCreate(new Date());
            entity.setDateModified(new Date());
            entity.setTariffPlanCode(input.getTariffPlanCode().trim());
            entity = tariffPlanDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Mã biểu phí đã tồn tại"));
        }
    }

    public ResponseEntity<?> delete(int id) {
        //Tìm xem có ladder nào đang dùng plan này không, nếu có thì không cho xóa
        if (tariffLadderDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có biểu phí con"));
        }

        //Tìm xem có phí không ladder nào đang dùng plan này không, nếu có thì không cho xóa
        if (tariffWithoutLadderDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có biểu phí con"));
        }

        //Tìm xem có bank nào đang dùng plan này không, nếu có thì không cho xóa
        if (tariffPlanParticipantDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do đang được dùng bởi TCTV"));
        }

        //Tìm xem có kênh miễn phí nào đang dùng plan này không, nếu có thì không cho xóa
        if (tariffPlanFreeChannelDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do đang được dùng bởi kênh miễn phí"));
        }

        tariffPlanDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }
}
