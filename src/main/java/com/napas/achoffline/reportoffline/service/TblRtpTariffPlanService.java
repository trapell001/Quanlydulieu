/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRtpTariffPlan;
import com.napas.achoffline.reportoffline.models.TblRtpTariffPlanDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.*;

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

/**
 * @author huynx
 */
@Service
public class TblRtpTariffPlanService {

    //private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Autowired
    private TblRtpTariffPlanDAO rtpTariffPlanDAO;

    @Autowired
    private TblRtpTariffLadderDAO rtpTariffLadderDAO;

    @Autowired
    private TblRtpTariffWithoutLadderDAO rtpTariffWithoutLadderDAO;

    @Autowired
    private TblRtpTariffPlanParticipantDAO rtpTariffPlanParticipantDAO;

    @Autowired
    private TblTariffPlanFreeChannelDAO tariffPlanFreeChannelDAO;

    @Autowired
    private ModelMapper mapper;

    private TblRtpTariffPlanDTO fromEntity(TblRtpTariffPlan entity) {
        TblRtpTariffPlanDTO dto = mapper.map(entity, TblRtpTariffPlanDTO.class);
        return dto;
    }

    private TblRtpTariffPlan fromDTO(TblRtpTariffPlanDTO dto) {
        TblRtpTariffPlan entity = mapper.map(dto, TblRtpTariffPlan.class);
        return entity;
    }

    public List<TblRtpTariffPlanDTO> list() {
        List<TblRtpTariffPlan> dbResult = rtpTariffPlanDAO.searchAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblRtpTariffPlanDTO get(int id) {
        return fromEntity(rtpTariffPlanDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblRtpTariffPlanDTO input) {

        try {
            TblRtpTariffPlan entity = rtpTariffPlanDAO.findById(id).orElse(null);
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
            entity = rtpTariffPlanDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Mã biểu phí đã tồn tại"));
        }

    }

    public ResponseEntity<?> post(TblRtpTariffPlanDTO input) {
        try {
            TblRtpTariffPlan entity = fromDTO(input);
            entity.setDateCreate(new Date());
            entity.setDateModified(new Date());
            entity.setTariffPlanCode(input.getTariffPlanCode().trim());
            entity = rtpTariffPlanDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Mã biểu phí đã tồn tại"));
        }
    }

    public ResponseEntity<?> delete(int id) {
        //Tìm xem có ladder nào đang dùng plan này không, nếu có thì không cho xóa
        if (rtpTariffLadderDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có biểu phí con"));
        }

        //Tìm xem có phí không ladder nào đang dùng plan này không, nếu có thì không cho xóa
        if (rtpTariffWithoutLadderDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có biểu phí con"));
        }

        //Tìm xem có bank nào đang dùng plan này không, nếu có thì không cho xóa
        if (rtpTariffPlanParticipantDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do đang được dùng bởi TCTV"));
        }

        //Tìm xem có kênh miễn phí nào đang dùng plan này không, nếu có thì không cho xóa
        if (tariffPlanFreeChannelDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do đang được dùng bởi kênh miễn phí"));
        }

        rtpTariffPlanDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }
}
