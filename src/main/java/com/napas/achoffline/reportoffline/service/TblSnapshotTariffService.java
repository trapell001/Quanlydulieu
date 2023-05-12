package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblSnapshotTariff;
import com.napas.achoffline.reportoffline.models.TblSnapshotTariffDTO;
import com.napas.achoffline.reportoffline.repository.TblSnapshotTariffDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TblSnapshotTariffService {

    @Autowired
    private TblSnapshotTariffDAO tblSnapshotTariffDAO;

    @Autowired
    private ModelMapper mapper;

    private TblSnapshotTariffDTO fromEntity(TblSnapshotTariff entity) {
        TblSnapshotTariffDTO dto = mapper.map(entity,TblSnapshotTariffDTO.class);
        return dto;
    }

    private TblSnapshotTariff fromDTO(TblSnapshotTariffDTO dto) {
        TblSnapshotTariff entity = mapper.map(dto, TblSnapshotTariff.class);
        return entity;
    }

    public List<TblSnapshotTariffDTO> find(Long tariffLadderId) {
        Set<TblSnapshotTariff> dbResult = tblSnapshotTariffDAO.findByTariffLadderId(tariffLadderId);
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }
}
