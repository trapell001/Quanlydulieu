package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblSnapshotTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TblSnapshotTariffDAO extends JpaRepository<TblSnapshotTariff,Long> {

    Set<TblSnapshotTariff> findByTariffLadderId(Long tariffLadderId);
}
