package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.HisBilling;
import com.napas.achoffline.reportoffline.entity.HisBillingParticipant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HisBillingParticipantDAO extends JpaRepository<HisBillingParticipant, Long> {
    @Query("SELECT q FROM HisBillingParticipant q WHERE " +
            " q.participantBic like %:participantBic%\n "
            + "AND (q.sessionId >= :sessionFrom or :sessionFrom is null) " +
            "AND (q.sessionId <= :sessionTo or :sessionTo is null)")
    Page<HisBillingParticipant> searchHisBillingPanticipant(@Param("participantBic") String participantBic,
                                                            @Param("sessionFrom") Long sessionFrom,
                                                            @Param("sessionTo") Long sessionTo,
                                                            Pageable pageable);

    @Query("SELECT q FROM HisBillingParticipant q WHERE " +
            "(q.sessionId >= :sessionFrom or :sessionFrom is null) " +
            "AND (q.sessionId <= :sessionTo or :sessionTo is null)" +
            "AND (q.participantBic like %:participantBic% or :participantBic is null)")
    List<HisBillingParticipant> graph(@Param("sessionFrom") Long sessionFrom,
                           @Param("sessionTo") Long sessionTo,
                            @Param("participantBic") String participantBic);
}
