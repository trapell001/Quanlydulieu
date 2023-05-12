package com.napas.achoffline.reportoffline.repository;


import com.napas.achoffline.reportoffline.entity.HisAlertEmailConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HisAlertEmailConfigDAO extends JpaRepository<HisAlertEmailConfig, Long> {
    @Query("SELECT h FROM HisAlertEmailConfig h" +
            " WHERE :alertCode is null or h.alertCode like %:alertCode%"
            + " and (:emailReceive is null or h.emailReceive like %:emailReceive%)"
            + " and (:participant is null or h.participant like %:participant%)"
    )
    public  Page<HisAlertEmailConfig> getAllElements(
            Pageable pageable,
            @Param("alertCode") String alertCode,
            @Param("emailReceive") String emailReceive,
            @Param("participant") String participant
    );
    Optional<HisAlertEmailConfig> findById(Long id);
    List<HisAlertEmailConfig> findAllByAlertCode(String alertCode);
}
