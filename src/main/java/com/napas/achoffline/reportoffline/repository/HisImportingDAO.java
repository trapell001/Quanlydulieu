package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.HisImporting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HisImportingDAO extends JpaRepository<HisImporting, Long> {

    @Query("select q from HisImporting q where " +
            "(q.dateCreate BETWEEN :dateFrom AND :dateTo or :dateFrom is null or :dateTo is null)" +
            "AND (q.typeOfState like %:typeOfState% or :typeOfState is null) ")
    Page<HisImporting> page(@Param("dateFrom") Date dateFrom,
                             @Param("dateTo") Date dateTo,
                             @Param("typeOfState") String typeOfState,
                             Pageable pageable);

    @Query("select q from HisImporting q where " +
            "(q.dateCreate BETWEEN :dateFrom AND :dateTo or :dateFrom is null or :dateTo is null)" +
            "AND (q.typeOfState like %:typeOfState% or :typeOfState is null) ")
    List<HisImporting> list(@Param("dateFrom") Date dateFrom,
                             @Param("dateTo") Date dateTo,
                             @Param("typeOfState") String typeOfState);
}
