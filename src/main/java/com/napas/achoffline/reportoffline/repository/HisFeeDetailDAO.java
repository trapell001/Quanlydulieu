package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.HisFeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HisFeeDetailDAO extends JpaRepository<HisFeeDetail, Long> {

    @Query("select q from HisFeeDetail q where " +
            "(q.sessionId >= :sessionFrom or :sessionFrom is null)" +
            "AND (q.sessionId <= :sessionTo or :sessionTo is null)")
    List<HisFeeDetail> searchHisFeeDetail(@Param("sessionFrom") Long sessionFrom,
                                          @Param("sessionTo") Long sessionTo);
}
