package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffPlan;
import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffPlanParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TblSnapshotTariffPlanParticipantDAO  extends JpaRepository<TblSnapshotTariffPlanParticipant, Long> {

    Set<TblSnapshotTariffPlanParticipant> findByTariffPlanId(Long tariffPlanId);
}
