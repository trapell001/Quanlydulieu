/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblTariffDNS;
import com.napas.achoffline.reportoffline.models.TblTariffDNSDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.napas.achoffline.reportoffline.repository.TblTariffDNSDAO;
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
public class TblTariffDNSService {

    @Autowired
    private TblTariffDNSDAO tariffDNSDAO;

    @Autowired
    private ModelMapper mapper;

    private TblTariffDNSDTO fromEntity(TblTariffDNS entity) {
        TblTariffDNSDTO dto = mapper.map(entity, TblTariffDNSDTO.class);
        return dto;
    }

    private TblTariffDNS fromDTO(TblTariffDNSDTO dto) {
        TblTariffDNS entity = mapper.map(dto, TblTariffDNS.class);
        return entity;
    }

    /**
     * Tìm xem tariff đang lưu có bị lặp với những cái đã tồn tại không không
     *
     * @param newLadder
     * @return
     */
    private boolean isNewTariffOverlap(TblTariffDNSDTO newTariff, Integer id, boolean isUpdate) {
        List<TblTariffDNS> listTariffDNS = tariffDNSDAO.findByTariffLadderId(newTariff.getTariffLadderId());

        if (isUpdate) {
            listTariffDNS = listTariffDNS.stream().filter(entity -> entity.getTariffId().compareTo(id)
                            != 0)
                    .collect(Collectors.toList());
        }

        return listTariffDNS.stream().filter(tariff -> tariff.getChannel().contentEquals(newTariff.getChannel()))
                .anyMatch(tariff
                        -> (tariff.getValueRangeMin() <= newTariff.getValueRangeMin() && tariff.getValueRangeMax() >= newTariff.getValueRangeMin())
                        || (tariff.getValueRangeMin() <= newTariff.getValueRangeMax() && tariff.getValueRangeMax() >= newTariff.getValueRangeMax())
                );
    }

    public List<TblTariffDNSDTO> list() {
        List<TblTariffDNS> dbResult = tariffDNSDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public List<TblTariffDNSDTO> find(int tariffLadderId) {
        List<TblTariffDNS> dbResult = tariffDNSDAO.findByTariffLadderId(tariffLadderId)
                .stream().sorted(Comparator.comparing(TblTariffDNS::getChannel).thenComparingLong(TblTariffDNS::getValueRangeMin))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblTariffDNSDTO get(int id) {
        return fromEntity(tariffDNSDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblTariffDNSDTO input) {
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
                    .body(new MessageResponse("Giá trị max bằng min, không thỏa mãn"));
        }

        if (!isNumTransRangeOverlap(input, input.getTariffLadderId(), false)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Biểu phí con bị trùng với một trong số các biểu phí đã cài đặt trước đó"));
        }

        TblTariffDNS entity = fromDTO(input);
        entity.setTariffId(id)
        ;
        entity.setDateModified(new Date());

        TblTariffDNS currentEntity = tariffDNSDAO.findById(id).orElse(null);
        entity.setDateCreated(currentEntity.getDateCreated());

        entity = tariffDNSDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> post(TblTariffDNSDTO input) {
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
                    .body(new MessageResponse("Giá trị max bằng min, không thỏa mãn"));
        }

        if (!isNumTransRangeOverlap(input, input.getTariffLadderId(), true)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Biểu phí con bị trùng với một trong số các biểu phí đã cài đặt trước đó"));
        }

        TblTariffDNS entity = fromDTO(input);
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

        entity = tariffDNSDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> delete(int id) {
        tariffDNSDAO.deleteById(id)
        ;
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }


    private boolean isNumTransRangeOverlap(TblTariffDNSDTO entity, int tariffLadderId, boolean check) {
        List<TblTariffDNS> list = tariffDNSDAO.findAll().stream().filter(item ->
                item.getTariffLadderId() == tariffLadderId).collect(Collectors.toList());
//        List<TblTariffWithoutLadderDNS> list = tariffDNSDAO.findAll();
        if (list.size() == 0)
            return true;
        if (check) {
            List<TblTariffDNS> newList = list.stream().filter(item ->
                    item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax()).collect(Collectors.toList());
            if (newList.size() == list.size())
                return true;
        }
        if (!check) {
            List<TblTariffDNS> newList = list.stream().filter(item -> (item.getTariffLadderId() != entity.getTariffLadderId()) &&
                    (item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax())).collect(Collectors.toList());
            if (newList.size() == list.size() - 1)
                return true;
        }
        return false;
    }
}