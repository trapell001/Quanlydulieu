package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.RptCamtDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RptCamtDetailDAO extends JpaRepository<RptCamtDetail, Long> {

    @Query("select R from RptCamtDetail R where " +
            "(R.mxSession >= :fromSession or :fromSession is null) " +
            "AND (R.mxSession <= :toSession or :toSession is null) " +
            "AND (R.mxTxidReference like %:reference% or :reference is null)" +
            "AND (R.mxParticipantAgent like %:participantAgent% or :participantAgent is null) " +
            "AND (R.aMsgType like %:msgType% or :msgType is null)" +
            "AND (R.mxPageNumber = :pageNumber or :pageNumber is null)"
            )
    Page<RptCamtDetail> getReportOnlineCampAchList(@Param("fromSession") Long fromSession,
                                                   @Param("toSession") Long  toSession,
                                                   @Param("reference") String reference,
                                                   @Param("participantAgent") String participantAgent,
                                                   @Param("msgType") String msgType,
                                                   @Param("pageNumber") Long pageNumber,
                                                   Pageable pageable
    );
    RptCamtDetail findByMxTxid(String txid);
}

