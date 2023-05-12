package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffLadder;
import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TblSnapshotTariffLadderDAO  extends JpaRepository<TblSnapshotTariffLadder,Long> {

    Set<TblSnapshotTariffLadder> findByTariffPlanId(Long tariffPlanId);
}
