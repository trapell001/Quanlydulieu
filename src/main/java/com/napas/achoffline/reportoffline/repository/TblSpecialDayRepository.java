package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.define.SpecialDayType;
import com.napas.achoffline.reportoffline.entity.TblSpecialDay;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TblSpecialDayRepository extends JpaRepository<TblSpecialDay, Integer> {
    @Query("""
           SELECT
               d
           FROM
               TblSpecialDay d
           WHERE
                   d.beginDate >= :dateFrom
               AND d.endDate <= :dateTo
               AND ( :dayType IS NULL
                     OR d.dayType = :dayType )
           """)
    public Page<TblSpecialDay> findDays(Pageable paging,
            @Param("dateFrom") Date dateFrom,
            @Param("dateTo") Date dateTo,
            @Param("dayType") SpecialDayType dayType);
}
