package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.DisputeCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Repository
public interface DisputeDAO extends JpaRepository<DisputeCase, Long> {

    @Query("""
        select d from DisputeCase d where 
        ((d.createDate between :beginCreateDate and :endCreateDate) or :beginCreateDate is null or :endCreateDate is null) 
        and ((d.modifDate between :beginModifDate and :endModifDate) or :beginModifDate is null or :endModifDate is null) 
        and ((d.responseDate between :beginResponseDate and :endResponseDate) or :beginResponseDate is null or :endResponseDate is null) 
        and (d.reference like %:reference% or :reference is null) 
        and (substring(d.participantAssigner,1,8) like %:claimant% or :claimant is null) 
        and (substring(d.participantAssignee,1,8) like %:claimee% or :claimee is null) 
        and (substring(d.participantRespondent,1,8) like %:respondent% or :respondent is null) 
        and (d.statusCode like %:status% or :status is null) 
        and (d.categoriesName like %:dispCat% or :dispCat is null) 
        and (d.requestNum like %:dispBatchReference% or :dispBatchReference is null) 
        and (d.txid like %:dispTxid% or :dispTxid is null) 
    """)
    List<DisputeCase> getDisputeList(
            @Param("beginCreateDate") Date beginCreateDate,
            @Param("endCreateDate") Date endCreateDate,
            @Param("reference") String reference,
            @Param("claimant") String claimant,
            @Param("claimee") String claimee,
            @Param("respondent") String respondent,
            @Param("status") String status,
            @Param("dispCat") String dispCat,
            @Param("dispBatchReference") String dispBatchReference,
            @Param("dispTxid") String dispTxid,
            @Param("beginModifDate") Date beginModifeDate,
            @Param("endModifDate") Date endModifDate,
            @Param("beginResponseDate") Date beginResponseDate,
            @Param("endResponseDate") Date endResponseDate
    );

    //    @Query("select * from dispute_case where " +
//            "((CREATE_DATE BETWEEN ?1 AND ?2) or ?1 is null or ?2 is null)" +
//            "AND (REFERENCE like %?3% or ?3 is null)" +
//            "AND (SUBSTR(PARTICIPANT_ASSIGNER,1,8) like %?4% or ?4 is null)" +
//            "AND (SUBSTR(PARTICIPANT_ASSIGNEE,1,8) like %?5% or ?5 is null)" +
//            "AND (SUBSTR(PARTICIPANT_RESPONDENT,1,8) like %?6% or ?6 is null)" +
//            "AND (STATUS_CODE like %?7% or ?7 is null)" +
//            "AND (CATEGORIES_NAME = ?8 or ?8 is null)" +
//            "AND (REQUEST_NUM like %?9% or ?9 is null)" +
//            "AND (TXID like %?10% or ?10 is null) ")
    @Query("""
        select d from DisputeCase d where 
        ((d.createDate between :beginCreateDate and :endCreateDate) or :beginCreateDate is null or :endCreateDate is null) 
        and ((d.modifDate between :beginModifDate and :endModifDate) or :beginModifDate is null or :endModifDate is null) 
        and ((d.responseDate between :beginResponseDate and :endResponseDate) or :beginResponseDate is null or :endResponseDate is null) 
        and (d.reference like %:reference% or :reference is null) 
        and (substring(d.participantAssigner,1,8) like %:claimant% or :claimant is null) 
        and (substring(d.participantAssignee,1,8) like %:claimee% or :claimee is null) 
        and (substring(d.participantRespondent,1,8) like %:respondent% or :respondent is null) 
        and (d.statusCode like %:status% or :status is null) 
        and (d.categoriesName like %:dispCat% or :dispCat is null) 
        and (d.requestNum like %:dispBatchReference% or :dispBatchReference is null) 
        and (d.txid like %:dispTxid% or :dispTxid is null) 
    """)
    Page<DisputeCase> getDisputePage(
            @Param("beginCreateDate") Date beginCreateDate,
            @Param("endCreateDate") Date endCreateDate,
            @Param("reference") String reference,
            @Param("claimant") String claimant,
            @Param("claimee") String claimee,
            @Param("respondent") String respondent,
            @Param("status") String status,
            @Param("dispCat") String dispCat,
            @Param("dispBatchReference") String dispBatchReference,
            @Param("dispTxid") String dispTxid,
            @Param("beginModifDate") Date beginModifeDate,
            @Param("endModifDate") Date endModifDate,
            @Param("beginResponseDate") Date beginResponseDate,
            @Param("endResponseDate") Date endResponseDate,
            Pageable pageable);

    DisputeCase findByReference(String reference);


    @Query(value = "select * from dispute_case where " +
            "(SUBSTR(PARTICIPANT_RESPONDENT,1,8) like %?1% or ?1 is null) " +
            "AND (TO_CHAR(CREATE_DATE + RESPONSE_TIME_NUM, 'YYYY-MM-DD') = TO_CHAR(?2, 'YYYY-MM-DD' ) OR ?2 is null) " +
            "AND (STATUS_CODE like %?3% or ?3 is null)"
            ,nativeQuery = true)
    List<DisputeCase> getDisputeWarningDue(String tctv, Date responseDate, String statusCode);

    @Query(value = "select * from dispute_case where " +
            "(SUBSTR(PARTICIPANT_RESPONDENT,1,8) like %?1% or ?1 is null) " +
            "AND (TO_CHAR(MODIF_DATE + 1, 'YYYY-MM-DD') = TO_CHAR(?2, 'YYYY-MM-DD' ) OR ?2 is null) " +
            "AND (STATUS_CODE like %?3% or ?3 is null)"
            ,nativeQuery = true)
    List<DisputeCase> getDisputeWarningExpi(String tctv, Date responseDate, String statusCode);
}
