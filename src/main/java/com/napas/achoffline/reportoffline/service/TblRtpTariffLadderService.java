/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRtpTariffLadder;
import com.napas.achoffline.reportoffline.entity.TblTariffWithoutLadder;
import com.napas.achoffline.reportoffline.models.TblRtpTariffLadderDTO;
import com.napas.achoffline.reportoffline.models.TblTariffWithoutLadderDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblRtpTariffDAO;
import com.napas.achoffline.reportoffline.repository.TblRtpTariffLadderDAO;
import java.util.Comparator;
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
public class TblRtpTariffLadderService {

    @Autowired
    private TblRtpTariffLadderDAO rtpTariffLadderDAO;

    @Autowired
    private TblRtpTariffDAO rtpTariffDAO;

    @Autowired
    private ModelMapper mapper;

    private TblRtpTariffLadderDTO fromEntity(TblRtpTariffLadder entity) {
        TblRtpTariffLadderDTO dto = mapper.map(entity, TblRtpTariffLadderDTO.class);
        return dto;
    }

    private TblRtpTariffLadder fromDTO(TblRtpTariffLadderDTO dto) {
        TblRtpTariffLadder entity = mapper.map(dto, TblRtpTariffLadder.class);
        return entity;
    }

    /**
     * Tìm xem ladder đang lưu có bị trùng khoảng với các ladder đã tồn tại
     * không
     *
     * @param newLadder
     * @return
     */
//    private boolean isNumTransRangeOverlap(TblRtpTariffLadderDTO newLadder, Integer id, boolean isUpdate) {
//        List<TblRtpTariffLadder> listLadder = rtpTariffLadderDAO.findByTariffPlanId(newLadder.getTariffPlanId());
//        if (isUpdate) {
//            listLadder = listLadder.stream().filter(entity -> entity.getTariffLadderId().compareTo(id)
    //!= 0)
//                    .collect(Collectors.toList());
//        }
//        return listLadder.stream().anyMatch(ladder
//                -> (ladder.getMinNumTrans() <= newLadder.getMinNumTrans() && ladder.getMaxNumTrans() >= newLadder.getMinNumTrans())
//                || (ladder.getMinNumTrans() <= newLadder.getMaxNumTrans() && ladder.getMaxNumTrans() >= newLadder.getMaxNumTrans())
//        );
//    }

    private boolean isNumTransRangeOverlap(TblRtpTariffLadderDTO entity, int tariffPlanId, boolean check) {
        List<TblRtpTariffLadder> list = rtpTariffLadderDAO.findAll().stream().filter(item ->
                item.getTariffPlanId() == tariffPlanId).collect(Collectors.toList());
//        List<TblTariffWithoutLadder> list = rtpTariffDAO.findAll();
        if (list.size() == 0)
            return true;
        if (check) {
            List<TblRtpTariffLadder> newList = list.stream().filter(item ->
                    item.getMaxNumTrans() < entity.getMinNumTrans() ||
                            item.getMinNumTrans() > entity.getMaxNumTrans()).collect(Collectors.toList());
            if (newList.size() == list.size())
                return true;
        }
        if (!check) {
            List<TblRtpTariffLadder> newList = list.stream().filter(item -> (item.getTariffLadderId() != entity.getTariffLadderId()) &&
                    (item.getMaxNumTrans() < entity.getMinNumTrans() ||
                            item.getMinNumTrans() > entity.getMaxNumTrans())).collect(Collectors.toList());
            if (newList.size() == list.size() - 1)
                return true;
        }
        return false;
    }

    public List<TblRtpTariffLadderDTO> list() {
        List<TblRtpTariffLadder> dbResult = rtpTariffLadderDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public List<TblRtpTariffLadderDTO> find(int tariffPlanId) {
        List<TblRtpTariffLadder> dbResult = rtpTariffLadderDAO.findByTariffPlanId(tariffPlanId)
                .stream().sorted(Comparator.comparingLong(TblRtpTariffLadder::getMinNumTrans))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblRtpTariffLadderDTO get(int id) {
        return fromEntity(rtpTariffLadderDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblRtpTariffLadderDTO input) {
        if (input.getMinNumTrans() > input.getMaxNumTrans()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max nhỏ hơn min, không thỏa mãn"));
        }
        if (input.getMinNumTrans() == input.getMaxNumTrans()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max bằng giá trị min, không thỏa mãn"));
        }
        if (!isNumTransRangeOverlap(input, input.getTariffPlanId(), false)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Khoảng bậc thang bị trùng với một trong số các bậc thang đã cài đặt trước đó"));
        }
        TblRtpTariffLadder entity = rtpTariffLadderDAO.findById(id).orElse(null);
        if(entity == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Biểu phí bậc thang không tồn tại"));
        }
        entity.setTariffPlanId(input.getTariffPlanId());
        entity.setMaxNumTrans(input.getMaxNumTrans());
        entity.setMinNumTrans(input.getMinNumTrans());
        entity.setTariffLadderDescription(input.getTariffLadderDescription());
        entity.setDateModified(new Date());
        rtpTariffLadderDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> post(TblRtpTariffLadderDTO input) {
        if (input.getMinNumTrans() > input.getMaxNumTrans()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max nhỏ hơn min, không thỏa mãn"));
        }

        if (input.getMinNumTrans() == input.getMaxNumTrans()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max bằng giá trị min, không thỏa mãn"));
        }

        if (!isNumTransRangeOverlap(input, input.getTariffPlanId(), true)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Khoảng bậc thang bị trùng với một trong số các bậc thang đã cài đặt trước đó"));
        }

        TblRtpTariffLadder entity = fromDTO(input);
        entity.setDateCreated(new Date());
        entity.setDateModified(new Date());
        entity = rtpTariffLadderDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> delete(int id) {
        //Tìm xem có tariff nào đang dùng ladder này không, nếu có thì không cho xóa
        if (rtpTariffDAO.findByTariffLadderId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có biểu phí con"));
        }

        rtpTariffLadderDAO.deleteById(id)
        ;
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }
}