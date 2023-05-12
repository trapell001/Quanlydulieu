package com.napas.achoffline.reportoffline.repository;


import com.napas.achoffline.reportoffline.entity.HisAlertConfig;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HisAlertConfigDAO extends JpaRepository<HisAlertConfig, Long> {
    @Query("SELECT h FROM HisAlertConfig h  WHERE :alertCode is null or h.alertCode like %:alertCode%"
            + " and (:alertName is null or h.alertName like %:alertName%)")
    public  Page<HisAlertConfig> getAllElements(
            Pageable pageable,
            @Param("alertCode") String alertCode,
            @Param("alertName") String alertName
    );
    Optional<HisAlertConfig> findByAlertCode(String alertCode);
}