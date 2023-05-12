/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblTariffLadderDNS;
import com.napas.achoffline.reportoffline.entity.TblTariffPlan;
import com.napas.achoffline.reportoffline.entity.TblTariffPlanDNS;
import com.napas.achoffline.reportoffline.models.TblTariffPlanDNSDTO;
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
public class TblTariffPlanDNSService {

    //private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Autowired
    private TblTariffPlanDNSDAO tariffPlanDNSDAO;

    @Autowired
    private TblTariffLadderDNSDAO tariffLadderDNSDAO;

    @Autowired
    private TblTariffWithoutLadderDNSDAO tariffWithoutLadderDNSDAO;

    @Autowired
    private TblTariffPlanParticipantDNSDAO tariffPlanParticipantDNSDAO;

//    @Autowired
//    private TblTariffPlanFreeChannelDNSDAO tariffPlanFreeChannelDNSDAO;

    @Autowired
    private ModelMapper mapper;

    private TblTariffPlanDNSDTO fromEntity(TblTariffPlanDNS entity) {
        TblTariffPlanDNSDTO dto = mapper.map(entity, TblTariffPlanDNSDTO.class);
        return dto;
    }

    private TblTariffPlanDNS fromDTO(TblTariffPlanDNSDTO dto) {
        TblTariffPlanDNS entity = mapper.map(dto, TblTariffPlanDNS.class);
        return entity;
    }

    public List<TblTariffPlanDNSDTO> list() {
        List<TblTariffPlanDNS> dbResult = tariffPlanDNSDAO.searchAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblTariffPlanDNSDTO get(int id) {
        return fromEntity(tariffPlanDNSDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblTariffPlanDNSDTO input) {

        try {
            TblTariffPlanDNS entity = tariffPlanDNSDAO.findById(id).orElse(null);
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
            entity = tariffPlanDNSDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Mã biểu phí đã tồn tại"));
        }

    }

    public ResponseEntity<?> post(TblTariffPlanDNSDTO input) {
        try {
            TblTariffPlanDNS entity = fromDTO(input);
            entity.setDateCreate(new Date());
            entity.setDateModified(new Date());
            entity.setTariffPlanCode(input.getTariffPlanCode().trim());
            entity = tariffPlanDNSDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Mã biểu phí đã tồn tại"));
        }
    }

    public ResponseEntity<?> delete(int id) {
        //Tìm xem có ladder nào đang dùng plan này không, nếu có thì không cho xóa
        if (tariffLadderDNSDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có biểu phí con"));
        }

        //Tìm xem có phí không ladder nào đang dùng plan này không, nếu có thì không cho xóa
        if (tariffWithoutLadderDNSDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có biểu phí con"));
        }

        //Tìm xem có bank nào đang dùng plan này không, nếu có thì không cho xóa
        if (tariffPlanParticipantDNSDAO.findByTariffPlanId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do đang được dùng bởi TCTV"));
        }

        //Tìm xem có kênh miễn phí nào đang dùng plan này không, nếu có thì không cho xóa
//        if (tariffPlanFreeChannelDNSDAO.findByTariffPlanId(id).size() > 0) {
//            System.out.println("vao day");
//            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
//                    .body(new MessageResponse("Không thể xóa do đang được dùng bởi kênh miễn phí"));
//        }
        tariffPlanDNSDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }
}
