package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.HisFeeDetail;
import com.napas.achoffline.reportoffline.entity.HisFeeDetailParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HisFeeDetailParticipantDAO extends JpaRepository<HisFeeDetailParticipant, Long> {

    @Query("select q from HisFeeDetailParticipant q where " +
            "(q.sessionId >= :sessionFrom or :sessionFrom is null)" +
            "AND (q.sessionId <= :sessionTo or :sessionTo is null)" +
            "AND q.participantBic like %:participantBic% or :participantBic is null")
    List<HisFeeDetailParticipant> searchHisFeeDetailParticipant(
            @Param("sessionFrom") Long sessionFrom
            , @Param("sessionTo") Long sessionTo
            , @Param("participantBic") String participantBic);
}
