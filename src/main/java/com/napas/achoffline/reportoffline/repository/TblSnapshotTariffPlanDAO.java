package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblSnapshotTariff;
import com.napas.achoffline.reportoffline.entity.TblSnapshotTariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblSnapshotTariffPlanDAO extends JpaRepository<TblSnapshotTariffPlan, Long> {

    Optional<TblSnapshotTariffPlan> findByTariffPlanId(Long tariffPlanId);

}
