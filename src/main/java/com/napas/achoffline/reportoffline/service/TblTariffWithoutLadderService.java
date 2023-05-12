/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRptoLadderValueLevel;
import com.napas.achoffline.reportoffline.entity.TblTariffWithoutLadder;
import com.napas.achoffline.reportoffline.models.TblRptoLadderValueLevelDTO;
import com.napas.achoffline.reportoffline.models.TblTariffWithoutLadderDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblTariffWithoutLadderDAO;
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
public class TblTariffWithoutLadderService {

    @Autowired
    private TblTariffWithoutLadderDAO tariffDAO;

    @Autowired
    private ModelMapper mapper;

    private TblTariffWithoutLadderDTO fromEntity(TblTariffWithoutLadder entity) {
        TblTariffWithoutLadderDTO dto = mapper.map(entity, TblTariffWithoutLadderDTO.class);
        return dto;
    }

    private TblTariffWithoutLadder fromDTO(TblTariffWithoutLadderDTO dto) {
        TblTariffWithoutLadder entity = mapper.map(dto, TblTariffWithoutLadder.class);
        return entity;
    }

    /**
     * Tìm xem tariff đang lưu có bị lặp với những cái đã tồn tại không không
     *
     * @param newLadder
     * @return
     */
    private boolean isNewTariffOverlap(TblTariffWithoutLadderDTO newTariff, Integer id, boolean isUpdate) {
        List<TblTariffWithoutLadder> listTariff = tariffDAO.findByTariffPlanId(newTariff.getTariffPlanId());

        if (isUpdate) {
            listTariff = listTariff.stream().filter(entity -> entity.getTariffId().compareTo(id)
                            != 0)
                    .collect(Collectors.toList());
        }

        return listTariff.stream().filter(tariff -> tariff.getChannel().contentEquals(newTariff.getChannel()))
                .anyMatch(tariff
                        -> (tariff.getValueRangeMin() <= newTariff.getValueRangeMin() && tariff.getValueRangeMax() >= newTariff.getValueRangeMin())
                        || (tariff.getValueRangeMin() <= newTariff.getValueRangeMax() && tariff.getValueRangeMax() >= newTariff.getValueRangeMax())
                );
    }

    public List<TblTariffWithoutLadderDTO> list() {
        List<TblTariffWithoutLadder> dbResult = tariffDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public List<TblTariffWithoutLadderDTO> find(int tariffPlanId) {
        List<TblTariffWithoutLadder> dbResult = tariffDAO.findByTariffPlanId(tariffPlanId)
                .stream().sorted(Comparator.comparing(TblTariffWithoutLadder::getChannel).thenComparingLong(TblTariffWithoutLadder::getValueRangeMin))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblTariffWithoutLadderDTO get(int id) {
        return fromEntity(tariffDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblTariffWithoutLadderDTO input) {
        //Giai đoạn này không cho phép đặt kênh, boc, ttc, service
        input.setChannel("-1");
        input.setBoc("CSDC");
        input.setTtc("001");
        input.setService("NRT");

        if (input.getValueRangeMin() > input.getValueRangeMax()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max nhỏ hơn min, không thỏa mãn"));
        }

        if (input.getValueRangeMin() == input.getValueRangeMax()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max bằng hơn min, không thỏa mãn"));
        }

        if (!isNumTransRangeOverlap(input,input.getTariffPlanId(),false)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Biểu phí bị trùng với một trong số các biểu phí đã tồn tại"));
        }

        TblTariffWithoutLadder entity = fromDTO(input);
        entity.setTariffId(id)
        ;
        entity.setDateModified(new Date());

        TblTariffWithoutLadder currentEntity = tariffDAO.findById(id).orElse(null);
        entity.setDateCreated(currentEntity.getDateCreated());

        entity = tariffDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> post(TblTariffWithoutLadderDTO input) {
        //Giai đoạn này không cho phép đặt kênh, boc, ttc, service
        input.setChannel("-1");
        input.setBoc("CSDC");
        input.setTtc("001");
        input.setService("NRT");

        if (input.getValueRangeMin() > input.getValueRangeMax()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max nhỏ hơn min, không thỏa mãn"));
        }

        if (input.getValueRangeMin() == input.getValueRangeMax()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new MessageResponse("Giá trị max bằng hơn min, không thỏa mãn"));
        }

        if (!isNumTransRangeOverlap(input,input.getTariffPlanId(),true)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Biểu phí bị trùng với một trong số các biểu phí đã tồn tại"));
        }

        TblTariffWithoutLadder entity = fromDTO(input);
        entity.setDateCreated(new Date());
        entity.setDateModified(new Date());

        if (entity.getService() == null) {
            entity.setService("NRT");
        }

        if (entity.getBoc() == null) {
            entity.setBoc("CSDC");
        }

        if (entity.getTtc() == null) {
            entity.setTtc("001");
        }

        if (entity.getPaymentCase() == null) {
            entity.setPaymentCase("P");
        }

        entity = tariffDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> delete(int id) {
        tariffDAO.deleteById(id)
        ;
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }

    private boolean isNumTransRangeOverlap(TblTariffWithoutLadderDTO entity, int tariffPlanId, boolean check) {
        List<TblTariffWithoutLadder> list = tariffDAO.findAll().stream().filter(item ->
                item.getTariffPlanId() == tariffPlanId).collect(Collectors.toList());
//        List<TblTariffWithoutLadder> list = tariffDAO.findAll();
        if (list.size() == 0)
            return true;
        if (check) {
            List<TblTariffWithoutLadder> newList = list.stream().filter(item ->
                    item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax()).collect(Collectors.toList());
            if (newList.size() == list.size())
                return true;
        }
        if (!check) {
            List<TblTariffWithoutLadder> newList = list.stream().filter(item -> (item.getTariffId() != entity.getTariffId()) &&
                    (item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax())).collect(Collectors.toList());
            if (newList.size() == list.size() - 1)
                return true;
        }
        return false;
    }
}