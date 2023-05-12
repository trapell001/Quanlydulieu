package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblParticipantReportOnline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TblParticipantReportOnlineDAO extends JpaRepository<TblParticipantReportOnline, Long> {

    @Query("SELECT P " +
            "FROM TblParticipantReportOnline P " +
            "WHERE" +
            " (P.participantBic like %:participantBic% OR :participantBic is null) ")
    Page<TblParticipantReportOnline> findParticipant(@Param("participantBic") String participantBic,
                                                     Pageable pageable);
}
