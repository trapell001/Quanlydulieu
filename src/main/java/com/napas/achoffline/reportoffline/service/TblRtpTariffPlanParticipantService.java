/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.define.ChannelType;
import com.napas.achoffline.reportoffline.entity.TblRtpTariffPlanParticipant;
import com.napas.achoffline.reportoffline.models.TblRtpTariffPlanParticipantDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.napas.achoffline.reportoffline.repository.TblRtpTariffPlanParticipantDAO;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

/**
 *
 * @author huynx
 */
@Service
public class TblRtpTariffPlanParticipantService {

    @Autowired
    private TblRtpTariffPlanParticipantDAO rtpTariffPlanParticipantDAO;

    @Autowired
    private ModelMapper mapper;

    private TblRtpTariffPlanParticipantDTO fromEntity(TblRtpTariffPlanParticipant entity) {
        TblRtpTariffPlanParticipantDTO dto = mapper.map(entity, TblRtpTariffPlanParticipantDTO.class);
        return dto;
    }

    private TblRtpTariffPlanParticipant fromDTO(TblRtpTariffPlanParticipantDTO dto) {
        TblRtpTariffPlanParticipant entity = mapper.map(dto, TblRtpTariffPlanParticipant.class);
        return entity;
    }

    public List<TblRtpTariffPlanParticipantDTO> list() {
        Sort sort = Sort.by(Sort.Direction.ASC, "bic", "channelType", "channelId");
        List<TblRtpTariffPlanParticipant> dbResult = rtpTariffPlanParticipantDAO.findAll(sort);
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public List<TblRtpTariffPlanParticipantDTO> listAchMemberOnly() {
        List<TblRtpTariffPlanParticipantDTO> result = list();
        return result.stream().filter(p -> p.getBic().endsWith("VNVN")).collect(Collectors.toList());
    }

    public TblRtpTariffPlanParticipantDTO get(int id) {
        return fromEntity(rtpTariffPlanParticipantDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblRtpTariffPlanParticipantDTO input) {
        List<TblRtpTariffPlanParticipant> listPlanByBic = rtpTariffPlanParticipantDAO.findByBic(input.getBic());

        TblRtpTariffPlanParticipant currentEntity = rtpTariffPlanParticipantDAO.findById(id).orElse(null);

        //Xóa khỏi list hiện tại
        listPlanByBic.remove(currentEntity);

        ResponseEntity<?> checkNewPlan = isNewPlanValid(input, listPlanByBic);

        if (checkNewPlan.getStatusCode() == HttpStatus.ACCEPTED) {
            currentEntity.setChannelId(input.getChannelId());
            currentEntity.setChannelType(input.getChannelType());
            currentEntity.setDateModified(new Date());
            currentEntity.setTariffPlanId(input.getTariffPlanId());

            currentEntity = rtpTariffPlanParticipantDAO.save(currentEntity);
            return ResponseEntity.ok(fromEntity(currentEntity));
        } else {
            return checkNewPlan;
        }
    }

    public ResponseEntity<?> post(TblRtpTariffPlanParticipantDTO input) {
        List<TblRtpTariffPlanParticipant> listPlanByBic = rtpTariffPlanParticipantDAO.findByBic(input.getBic());

        ResponseEntity<?> checkNewPlan = isNewPlanValid(input, listPlanByBic);

        if (checkNewPlan.getStatusCode() == HttpStatus.ACCEPTED) {
            TblRtpTariffPlanParticipant entity = fromDTO(input);
            entity.setDateCreated(new Date());
            entity.setDateModified(new Date());

            entity = rtpTariffPlanParticipantDAO.save(entity);
            return ResponseEntity.ok(fromEntity(entity));
        } else {
            return checkNewPlan;
        }

    }

    private ResponseEntity<?> isNewPlanValid(TblRtpTariffPlanParticipantDTO input, List<TblRtpTariffPlanParticipant> listPlanByBic) {
        ChannelType newChannelType = input.getChannelType();

        //Không cho phép chọn type ALL
        if (newChannelType == ChannelType.ALL) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Không chập nhận type ALL"));
        }

        //Xem dã tồn tại type All chưa
        boolean typeAllExisted = listPlanByBic.stream().anyMatch(p -> p.getChannelType() == ChannelType.ALL);

        if (typeAllExisted) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Không thể tạo mới biểu phí do đã tồn tại biểu phí cho kênh"));
        }

        //Kiểm tra channel type mới
        if (newChannelType == ChannelType.NORMAL) {
            //Kiểm tra xem đã tồn tại plan kênh thông thường nào chưa
            boolean typeNormalExisted = listPlanByBic.stream().anyMatch(p -> p.getChannelType() == ChannelType.NORMAL);

            if (typeNormalExisted) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MessageResponse("Không thể tạo mới biểu phí do đã tồn tại biểu phí cho kênh thông thường"));
            }
        } else if (newChannelType == ChannelType.ALL) {
            //Kiểm tra xem đã tồn tại bất kỳ plan nào chưa
            if (listPlanByBic.size() > 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MessageResponse("Không thể tạo mới biểu phí do đã tồn tại biểu phí cho loại khác"));
            }
        } else {
            boolean channelExisted = listPlanByBic.stream().anyMatch(p -> (p.getChannelType() == ChannelType.SPECIAL && p.getChannelId().contentEquals(input.getChannelId())));
            if (channelExisted) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MessageResponse("Không thể tạo mới biểu phí với kênh này do đã tồn tại trước đó"));
            }
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<?> delete(int id) {
        rtpTariffPlanParticipantDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa TCTV thành công"));
    }
}
