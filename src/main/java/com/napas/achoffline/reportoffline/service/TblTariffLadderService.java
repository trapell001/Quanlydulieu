/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblTariffLadder;
import com.napas.achoffline.reportoffline.entity.TblTariffWithoutLadder;
import com.napas.achoffline.reportoffline.models.TblTariffLadderDTO;
import com.napas.achoffline.reportoffline.models.TblTariffWithoutLadderDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblTariffDAO;
import com.napas.achoffline.reportoffline.repository.TblTariffLadderDAO;
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
public class TblTariffLadderService {

    @Autowired
    private TblTariffLadderDAO tariffLadderDAO;

    @Autowired
    private TblTariffDAO tariffDAO;

    @Autowired
    private ModelMapper mapper;

    private TblTariffLadderDTO fromEntity(TblTariffLadder entity) {
        TblTariffLadderDTO dto = mapper.map(entity, TblTariffLadderDTO.class);
        return dto;
    }

    private TblTariffLadder fromDTO(TblTariffLadderDTO dto) {
        TblTariffLadder entity = mapper.map(dto, TblTariffLadder.class);
        return entity;
    }

    /**
     * Tìm xem ladder đang lưu có bị trùng khoảng với các ladder đã tồn tại
     * không
     *
     * @param newLadder
     * @return
     */
//    private boolean isNumTransRangeOverlap(TblTariffLadderDTO newLadder, Integer id, boolean isUpdate) {
//        List<TblTariffLadder> listLadder = tariffLadderDAO.findByTariffPlanId(newLadder.getTariffPlanId());
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

    private boolean isNumTransRangeOverlap(TblTariffLadderDTO entity, int tariffPlanId, boolean check) {
        List<TblTariffLadder> list = tariffLadderDAO.findAll().stream().filter(item ->
                item.getTariffPlanId() == tariffPlanId).collect(Collectors.toList());
//        List<TblTariffWithoutLadder> list = tariffDAO.findAll();
        if (list.size() == 0)
            return true;
        if (check) {
            List<TblTariffLadder> newList = list.stream().filter(item ->
                    item.getMaxNumTrans() < entity.getMinNumTrans() ||
                            item.getMinNumTrans() > entity.getMaxNumTrans()).collect(Collectors.toList());
            if (newList.size() == list.size())
                return true;
        }
        if (!check) {
            List<TblTariffLadder> newList = list.stream().filter(item -> (item.getTariffLadderId() != entity.getTariffLadderId()) &&
                    (item.getMaxNumTrans() < entity.getMinNumTrans() ||
                            item.getMinNumTrans() > entity.getMaxNumTrans())).collect(Collectors.toList());
            if (newList.size() == list.size() - 1)
                return true;
        }
        return false;
    }

    public List<TblTariffLadderDTO> list() {
        List<TblTariffLadder> dbResult = tariffLadderDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public List<TblTariffLadderDTO> find(int tariffPlanId) {
        List<TblTariffLadder> dbResult = tariffLadderDAO.findByTariffPlanId(tariffPlanId)
                .stream().sorted(Comparator.comparingLong(TblTariffLadder::getMinNumTrans))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblTariffLadderDTO get(int id) {
        return fromEntity(tariffLadderDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblTariffLadderDTO input) {
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
        TblTariffLadder entity = tariffLadderDAO.findById(id).orElse(null);
        if(entity == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Biểu phí bậc thang không tồn tại"));
        }
        entity.setTariffPlanId(input.getTariffPlanId());
        entity.setMaxNumTrans(input.getMaxNumTrans());
        entity.setMinNumTrans(input.getMinNumTrans());
        entity.setTariffLadderDescription(input.getTariffLadderDescription());
        entity.setDateModified(new Date());
        tariffLadderDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> post(TblTariffLadderDTO input) {
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

        TblTariffLadder entity = fromDTO(input);
        entity.setDateCreated(new Date());
        entity.setDateModified(new Date());
        entity = tariffLadderDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> delete(int id) {
        //Tìm xem có tariff nào đang dùng ladder này không, nếu có thì không cho xóa
        if (tariffDAO.findByTariffLadderId(id).size() > 0) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new MessageResponse("Không thể xóa do có biểu phí con"));
        }

        tariffLadderDAO.deleteById(id)
        ;
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }
}