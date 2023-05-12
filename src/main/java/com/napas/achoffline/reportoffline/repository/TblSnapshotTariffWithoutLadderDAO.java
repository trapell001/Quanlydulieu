package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffWithoutLadder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TblSnapshotTariffWithoutLadderDAO extends JpaRepository<TblSnapshotTariffWithoutLadder,Long> {

    Set<TblSnapshotTariffWithoutLadder> findByTariffPlanId(Long tariffPlanId);
}
