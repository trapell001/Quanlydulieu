/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRtpTariff;
import com.napas.achoffline.reportoffline.models.TblRtpTariffDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.TblRtpTariffDAO;
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
public class TblRtpTariffService {

    @Autowired
    private TblRtpTariffDAO rtpTariffDAO;

    @Autowired
    private ModelMapper mapper;

    private TblRtpTariffDTO fromEntity(TblRtpTariff entity) {
        TblRtpTariffDTO dto = mapper.map(entity, TblRtpTariffDTO.class);
        return dto;
    }

    private TblRtpTariff fromDTO(TblRtpTariffDTO dto) {
        TblRtpTariff entity = mapper.map(dto, TblRtpTariff.class);
        return entity;
    }

    private boolean isNewTariffOverlap(TblRtpTariffDTO newTariff, Integer id, boolean isUpdate) {
        List<TblRtpTariff> listTariff = rtpTariffDAO.findByTariffLadderId(newTariff.getTariffLadderId());

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

    public List<TblRtpTariffDTO> list() {
        List<TblRtpTariff> dbResult = rtpTariffDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public List<TblRtpTariffDTO> find(int tariffLadderId) {
        List<TblRtpTariff> dbResult = rtpTariffDAO.findByTariffLadderId(tariffLadderId)
                .stream().sorted(Comparator.comparing(TblRtpTariff::getChannel).thenComparingLong(TblRtpTariff::getValueRangeMin))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblRtpTariffDTO get(int id) {
        return fromEntity(rtpTariffDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblRtpTariffDTO input) {
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

        TblRtpTariff entity = fromDTO(input);
        entity.setTariffId(id)
        ;
        entity.setDateModified(new Date());

        TblRtpTariff currentEntity = rtpTariffDAO.findById(id).orElse(null);
        entity.setDateCreated(currentEntity.getDateCreated());

        entity = rtpTariffDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> post(TblRtpTariffDTO input) {
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

        TblRtpTariff entity = fromDTO(input);
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

        entity = rtpTariffDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> delete(int id) {
        rtpTariffDAO.deleteById(id)
        ;
        return ResponseEntity.ok(new MessageResponse("Xóa biểu phí thành công"));
    }


    private boolean isNumTransRangeOverlap(TblRtpTariffDTO entity, int tariffLadderId, boolean check) {
        List<TblRtpTariff> list = rtpTariffDAO.findAll().stream().filter(item ->
                item.getTariffLadderId() == tariffLadderId).collect(Collectors.toList());
//        List<TblRtpTariffWithoutLadder> list = rtpTariffDAO.findAll();
        if (list.size() == 0)
            return true;
        if (check) {
            List<TblRtpTariff> newList = list.stream().filter(item ->
                    item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax()).collect(Collectors.toList());
            if (newList.size() == list.size())
                return true;
        }
        if (!check) {
            List<TblRtpTariff> newList = list.stream().filter(item -> (item.getTariffLadderId() != entity.getTariffLadderId()) &&
                    (item.getValueRangeMax() < entity.getValueRangeMin() ||
                            item.getValueRangeMin() > entity.getValueRangeMax())).collect(Collectors.toList());
            if (newList.size() == list.size() - 1)
                return true;
        }
        return false;
    }
}