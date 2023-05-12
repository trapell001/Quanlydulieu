package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffWithoutLadder;
import com.napas.achoffline.reportoffline.models.TblSnapshotTariffWithoutLadderDTO;
import com.napas.achoffline.reportoffline.repository.TblSnapshotTariffWithoutLadderDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TblSnapshotTariffWithoutLadderService {

    @Autowired
    private TblSnapshotTariffWithoutLadderDAO tblSnapshotTariffWithoutLadderDAO;

    @Autowired
    private ModelMapper mapper;

    private TblSnapshotTariffWithoutLadderDTO fromEntity(TblSnapshotTariffWithoutLadder entity) {
        TblSnapshotTariffWithoutLadderDTO dto = mapper.map(entity,TblSnapshotTariffWithoutLadderDTO.class);
        return dto;
    }

    public List<TblSnapshotTariffWithoutLadderDTO> find(Long tariffPlanId) {
        Set<TblSnapshotTariffWithoutLadder> dbResult = tblSnapshotTariffWithoutLadderDAO.findByTariffPlanId(tariffPlanId);
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }
}
