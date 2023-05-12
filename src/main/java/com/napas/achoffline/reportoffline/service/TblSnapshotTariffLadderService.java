package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffLadder;
import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffPlan;
import com.napas.achoffline.reportoffline.models.TblSnapshotTariffLadderDTO;
import com.napas.achoffline.reportoffline.models.TblSnapshotTariffPlanDTO;
import com.napas.achoffline.reportoffline.repository.TblSnapshotTariffLadderDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TblSnapshotTariffLadderService {

    @Autowired
    private TblSnapshotTariffLadderDAO tblSnapshotTariffLadderDAO;

    @Autowired
    private ModelMapper mapper;

    private TblSnapshotTariffLadderDTO fromEntity(TblSnapshotTariffLadder entity) {
        TblSnapshotTariffLadderDTO dto = mapper.map(entity,TblSnapshotTariffLadderDTO.class);
        return dto;
    }

    public List<TblSnapshotTariffLadderDTO> find(Long tariffPlanId) {
        Set<TblSnapshotTariffLadder> dbResult = tblSnapshotTariffLadderDAO.findByTariffPlanId(tariffPlanId);
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }
}
