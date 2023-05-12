/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblTariffPlanFreeChannel;
import com.napas.achoffline.reportoffline.models.TblTariffPlanFreeChannelDTO;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.napas.achoffline.reportoffline.repository.TblTariffPlanFreeChannelDAO;
import java.util.Comparator;

/**
 *
 * @author huynx
 */
@Service
public class TblTariffPlanFreeChannelService {

    @Autowired
    private TblTariffPlanFreeChannelDAO tariffPlanFreeChannelDAO;

    @Autowired
    private ModelMapper mapper;

    private TblTariffPlanFreeChannelDTO fromEntity(TblTariffPlanFreeChannel entity) {
        TblTariffPlanFreeChannelDTO dto = mapper.map(entity, TblTariffPlanFreeChannelDTO.class);
        return dto;
    }

    private TblTariffPlanFreeChannel fromDTO(TblTariffPlanFreeChannelDTO dto) {
        TblTariffPlanFreeChannel entity = mapper.map(dto, TblTariffPlanFreeChannel.class);
        return entity;
    }

    public List<TblTariffPlanFreeChannelDTO> find(int tariffPlanId) {
        List<TblTariffPlanFreeChannel> dbResult = tariffPlanFreeChannelDAO.findByTariffPlanId(tariffPlanId)
                .stream().sorted(Comparator.comparing(TblTariffPlanFreeChannel::getChannelId))
                .collect(Collectors.toList());
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }

    public TblTariffPlanFreeChannelDTO get(int id) {
        return fromEntity(tariffPlanFreeChannelDAO.findById(id).orElse(null));
    }

    public ResponseEntity<?> put(int id, TblTariffPlanFreeChannelDTO input) {
        TblTariffPlanFreeChannel currentEntity = tariffPlanFreeChannelDAO.findById(id).orElse(null);
        currentEntity.setDateModified(new Date());

        currentEntity.setDescription(input.getDescription());
        currentEntity.setTariffPlanId(input.getTariffPlanId());
        currentEntity.setChannelId(input.getChannelId());

        currentEntity = tariffPlanFreeChannelDAO.save(currentEntity);
        return ResponseEntity.ok(fromEntity(currentEntity));
    }

    public ResponseEntity<?> post(TblTariffPlanFreeChannelDTO input) {
        TblTariffPlanFreeChannel entity = fromDTO(input);
        entity.setDateCreated(new Date());
        entity.setDateModified(new Date());
        entity = tariffPlanFreeChannelDAO.save(entity);
        return ResponseEntity.ok(fromEntity(entity));
    }

    public ResponseEntity<?> delete(int id) {
        tariffPlanFreeChannelDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa thành công"));
    }
}
