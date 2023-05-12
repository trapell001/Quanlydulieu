package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.HisBatchPayments;
import com.napas.achoffline.reportoffline.models.BatchPaymentsStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface HisBatchPaymentsDAO extends JpaRepository<HisBatchPayments, Long> {

    @Query("select distinct h from HisBatchPayments h, HisBatchInstr q where " +
            "((h.processDate between ?1 and ?2) or ?1 is null or ?2 is null) " +
            "AND (h.mxMsgid like %?3% or ?3 is null) " +
            "AND (h.sessionId >= ?4 or ?4 is null) " +
            "AND (h.sessionId <= ?5 or ?5 is null) " +
            "AND (h.transStatus like %?6% or ?6 is null) " +
            "AND (h.mxType like %?7% or ?7 is null) " +
            "AND (h.mxBankOpCode like %?8% or ?8 is null) " +
            "AND (h.mxTransTypeCode like %?9% or ?9 is null) " +
            "AND (h.viewStatus like %?10% or ?10 is null) " +
            "AND (h.docId = q.docId) " +
            "AND (q.mxDebtorAgent like %?11% or ?11 is null) " +
            "AND (q.mxCreditorAgent like %?12% or ?12 is null) " +
            "AND (q.mxCreditorAccountType like %?13% or ?13 is null) ")
    Page<HisBatchPayments> searchBatchPayments(Date dateBegin, Date dateEnd, String msgId, Long sessionFrom,
                                               Long sessionTo, String status, String msgType, String boc, String ttc,
                                               String viewStatus, String tcpl, String tcnl, String creditorType,
                                               Pageable pageable);

    @Query("select distinct h from HisBatchPayments h, HisBatchInstr q where " +
            "((h.processDate between ?1 and ?2) or ?1 is null or ?2 is null) " +
            "AND (h.mxMsgid like %?3% or ?3 is null) " +
            "AND (h.sessionId >= ?4 or ?4 is null) " +
            "AND (h.sessionId <= ?5 or ?5 is null) " +
            "AND (h.transStatus like %?6% or ?6 is null) " +
            "AND (h.mxType like %?7% or ?7 is null) " +
            "AND (h.mxBankOpCode like %?8% or ?8 is null) " +
            "AND (h.mxTransTypeCode like %?9% or ?9 is null) " +
            "AND (h.viewStatus like %?10% or ?10 is null) " +
            "AND (h.docId = q.docId) " +
            "AND (q.mxDebtorAgent like %?11% or ?11 is null) " +
            "AND (q.mxCreditorAgent like %?12% or ?12 is null) " +
            "AND (q.mxCreditorAccountType like %?13% or ?13 is null) ")
    List<HisBatchPayments> exportExcel(Date dateBegin, Date dateEnd, String msgId, Long sessionFrom,
                                       Long sessionTo, String status, String msgType, String boc, String ttc,
                                       String viewStatus, String tcpl, String tcnl, String creditorType);

    @Query("select h from HisBatchPayments h where h.docId = ?1")
    HisBatchPayments findByADocId(Long adocId);

    @Query("""
        select new com.napas.achoffline.reportoffline.models.BatchPaymentsStatistic(h1.mxTransTypeCode,
        h2.mxDebtorAgent,h1.transStatus, h1.errorCode, count(distinct h1.docId)) 
        from HisBatchPayments h1, HisBatchInstr h2 where 
        h1.processDate between :dateBegin and :dateEnd
        and (YEAR(h1.processDate) = :year or :year is null) 
        and (MONTH(h1.processDate) = :month or :month is null) 
        and (DAY(h1.processDate) = :day or :day is null) 
        and ('findAll' in (:listBoc) or h1.mxBankOpCode in (:listBoc)) 
        and ('findAll' in (:listTtc) or h1.mxTransTypeCode in (:listTtc)) 
        and ('findAll' in (:listTransType) or h1.mxType in (:listTransType)) 
        and ('findAll' in (:listTransStatus) or h1.transStatus in (:listTransStatus)) 
        and (h1.docId = h2.docId)
        and ('findAll' in (:listCreditorType) or h2.mxCreditorAccountType in (:listCreditorType)) 
        and ('findAll' in (:listInstructing) or h2.mxDebtorAgent in (:listInstructing))
        group by h2.mxDebtorAgent, h1.mxTransTypeCode, h1.transStatus, h1.errorCode
        order by h2.mxDebtorAgent, h1.mxTransTypeCode, h1.transStatus, h1.errorCode                                                                            
    """)
    List<BatchPaymentsStatistic> findStatisticBatchPayments(
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("day") Integer day,
            @Param("listBoc") List<String> listBoc,
            @Param("listTtc") List<String> listTtc,
            @Param("listTransType") List<String> listTransType,
            @Param("listCreditorType") List<String> listCreditorType,
            @Param("listTransStatus") List<String> listTransStatus,
            @Param("listInstructing") List<String> listInstructing,
            @Param("dateBegin") Date begin,
            @Param("dateEnd") Date end
    );
}
