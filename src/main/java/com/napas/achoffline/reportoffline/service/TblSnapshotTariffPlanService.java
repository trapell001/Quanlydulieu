package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel3;
import com.napas.achoffline.reportoffline.entity.TblRptoLadder;
import com.napas.achoffline.reportoffline.entity.TblSnapshotTariff;
import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffPlan;
import com.napas.achoffline.reportoffline.models.TblRptoLadderDTO;
import com.napas.achoffline.reportoffline.models.TblSnapshotTariffDTO;
import com.napas.achoffline.reportoffline.models.TblSnapshotTariffPlanDTO;
import com.napas.achoffline.reportoffline.repository.TblSnapshotTariffPlanDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TblSnapshotTariffPlanService {
    @Autowired
    private TblSnapshotTariffPlanDAO tblSnapshotTariffPlanDAO;
    @Autowired
    private ModelMapper mapper;

    private TblSnapshotTariffPlanDTO fromEntity(TblSnapshotTariffPlan entity) {
        TblSnapshotTariffPlanDTO dto = mapper.map(entity, TblSnapshotTariffPlanDTO.class);
        return dto;
    }

    public Optional<TblSnapshotTariffPlan> find(Long tariffPlanId) {
        return tblSnapshotTariffPlanDAO.findByTariffPlanId(tariffPlanId);
    }
    public List<TblSnapshotTariffPlanDTO> list() {
        List<TblSnapshotTariffPlan> dbResult = tblSnapshotTariffPlanDAO.findAll();
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }
}
