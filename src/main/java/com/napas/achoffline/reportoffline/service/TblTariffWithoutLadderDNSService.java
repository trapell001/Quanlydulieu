/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblTariffWithoutLadderDNS;
import com.napas.achoffline.reportoffline.models.TblTariffWithoutLadderDNSDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblTariffWithoutLadderDNSDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author huynx
 */
@Service
public class TblTariffWithoutLadderDNSService {

    @Autowired
    private TblTariffWithoutLadderDNSDAO tariffDNSDAO;

    @Autowired
    private ModelMapper mapper;

    private TblTariffWithoutLadderDNSDTO fromEntity(TblTariffWithoutLadderDNS entity) {
        TblTariffWithoutLadderDNSDTO dto = mapper.map(entity, TblTariffWithoutLadderDNSDTO.class);
        return dto;
    }

    private TblTariffWithoutLadderDNS fromDTO(TblTariffWithoutLadderDNSDTO dto) {
        TblTariffWithoutLadderDNS entity = mapper.map(dto, TblTariffWithoutLadderDNS.class);
        return entity;
    }

    /**
     * Tìm xem tariff đang lưu có bị lặp với những cái đã tồn tại không không
     *
     * @param
     *
     * @return
     */
    private boolean isNewTariffOverlap(TblTariffWithoutLadderDNSDTO newTariff, Integer id, boolean isUpdate) {
        List<TblTariffWithoutLadderDNS> listTariff = tariffDNSDAO.findByTariffPlanId(newTariff.getTariffPlanId());

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

    public List<TblTariffWithoutLadderDNSDTO> list() {
        List<TblTariffWithoutLadderDNS> dbResult = tariffDNSDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public List<TblTariffWithoutLadderDNSDTO> find(int tariffPlanId) {
        List<TblTariffWithoutLadderDNS> dbResult = tariffDNSDAO.findByTariffPlanId(tariffPlanId)
                .stream().sorted(Comparator.comparing(TblTariffWithoutLadderDNS::getChannel).thenComparingLong(TblTariffWithoutLadderDNS::getValueRangeMin))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblTariffWithoutLadderDNSDTO get(int id) {
        return fromEntity(tariffDNSDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblTariffWithoutLadderDNSDTO input) {
        //Giai đoạn này không cho phép đặt kênh, boc, ttc, service
        input.setChannel("-1");
        input.setBoc("CSDC");
        input.setTtc("001");
        input.setService("DNS");

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

        TblTariffWithoutLadderDNS entity = fromDTO(input);
        entity.setTariffId(id)
        ;
        entity.setDateModified(new Date());

        TblTariffWithoutLadderDNS currentEntity = tariffDNSDAO.findById(id).orElse(null);
        entity.setDateCreated(currentEntity.getDateCreated());

        entity = tariffDNSDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> post(TblTariffWithoutLadderDNSDTO input) {
        //Giai đoạn này không cho phép đặt kênh, boc, ttc, service
        input.setChannel("-1");
        input.setBoc("CSDC");
        input.setTtc("001");
        input.setService("DNS");

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

        TblTariffWithoutLadderDNS entity = fromDTO(input);
        entity.setDateCreated(new Date());
        entity.setDateModified(new Date());

        if (entity.getService() == null) {
            entity.setService("DNS");
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

        entity = tariffDNSDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> delete(int id) {
        tariffDNSDAO.deleteById(id)
        ;
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }

    private boolean isNumTransRangeOverlap(TblTariffWithoutLadderDNSDTO entity, int tariffPlanId, boolean check) {
        List<TblTariffWithoutLadderDNS> list = tariffDNSDAO.findAll().stream().filter(item ->
                item.getTariffPlanId() == tariffPlanId).collect(Collectors.toList());
//        List<TblTariffWithoutLadderDNS> list = tariffDNSDAO.findAll();
        if (list.size() == 0)
            return true;
        if (check) {
            List<TblTariffWithoutLadderDNS> newList = list.stream().filter(item ->
                    item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax()).collect(Collectors.toList());
            if (newList.size() == list.size())
                return true;
        }
        if (!check) {
            List<TblTariffWithoutLadderDNS> newList = list.stream().filter(item -> (item.getTariffId() != entity.getTariffId()) &&
                    (item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax())).collect(Collectors.toList());
            if (newList.size() == list.size() - 1)
                return true;
        }
        return false;
    }
}