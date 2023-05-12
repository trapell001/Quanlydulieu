package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffPlanParticipant;
import com.napas.achoffline.reportoffline.models.TblSnapshotTariffPlanParticipantDTO;
import com.napas.achoffline.reportoffline.repository.TblSnapshotTariffPlanParticipantDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TblSnapshotTariffPlanParticipantService {
    @Autowired
    private TblSnapshotTariffPlanParticipantDAO tblSnapshotTariffPlanParticipantDAO;
    @Autowired
    private ModelMapper mapper;

    private TblSnapshotTariffPlanParticipantDTO fromEntity(TblSnapshotTariffPlanParticipant entity) {
        TblSnapshotTariffPlanParticipantDTO dto = mapper.map(entity,TblSnapshotTariffPlanParticipantDTO.class);
        return dto;
    }

    public List<TblSnapshotTariffPlanParticipantDTO> find(Long tariffPlanId) {
        Set<TblSnapshotTariffPlanParticipant> dbResult = tblSnapshotTariffPlanParticipantDAO.findByTariffPlanId(tariffPlanId);
        return dbResult.stream().map(entity -> fromEntity(entity)).collect(Collectors.toList());
    }
}
