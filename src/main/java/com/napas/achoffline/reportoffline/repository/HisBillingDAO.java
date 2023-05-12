package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.HisBilling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HisBillingDAO extends JpaRepository<HisBilling,Long> {

    @Query("SELECT q FROM HisBilling q WHERE " +
            "(q.sessionId >= :sessionFrom or :sessionFrom is null) " +
            "AND (q.sessionId <= :sessionTo or :sessionTo is null) ")
    Page<HisBilling> searchHisBilling(@Param("sessionFrom") Long sessionFrom,
                                             @Param("sessionTo") Long sessionTo,
                                             Pageable pageable);

    @Query("SELECT q FROM HisBilling q WHERE " +
            "(q.sessionId >= :sessionFrom or :sessionFrom is null) " +
            "AND (q.sessionId <= :sessionTo or :sessionTo is null)")
    List<HisBilling> graph(@Param("sessionFrom") Long sessionFrom,
                           @Param("sessionTo") Long sessionTo);
}
