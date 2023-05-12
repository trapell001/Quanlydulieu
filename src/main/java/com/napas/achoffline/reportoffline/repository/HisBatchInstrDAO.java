package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.HisBatchInstr;
import com.napas.achoffline.reportoffline.models.BatchInstrPaymentsStatistic;
import com.napas.achoffline.reportoffline.models.BatchPaymentsStatistic;
import com.napas.achoffline.reportoffline.models.BatchInstrStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface HisBatchInstrDAO extends JpaRepository<HisBatchInstr, Long> {

    @Query("select h from HisBatchInstr h where " +
            "h.docId = ?1 " +
            "AND (h.mxDebtorAgent like %?2% or ?2 is null) " +
            "AND (h.mxCreditorAgent like %?3% or ?3 is null) " +
            "AND (h.mxCreditorAccountType like %?4% or ?4 is null)")
    List<HisBatchInstr> searchBatchInstr(Long aDocId, String tcpl, String tcnl, String creditorType);

    @Query("select h from HisBatchInstr h where " +
            "((h.processDate between :begin and :end) or :begin is null or :end is null) " +
            "AND (h.docId = :aDocId or :aDocId is null) " +
            "AND (h.mxTxid like %:txid% or :txid is null) " +
            "AND (h.status like %:status% or :status is null) " +
            "AND (h.mxDebtorName like %:debtorName% or :debtorName is null) " +
            "AND (h.mxDebtorAccountType like %:debtorType% or :debtorType is null) " +
            "AND (h.mxDebtorAccount like %:debtorAccount% or :debtorAccount is null) " +
            "AND (h.mxCreditorName like %:creditorName% or :creditorName is null) " +
            "AND (h.mxCreditorAccountType like %:creditorType% or :creditorType is null) " +
            "AND (h.mxCreditorAccount like %:creditorAccount% or :creditorAccount is null) " +
            "AND (h.viewStatus like %:viewStatus% or :viewStatus is null) " +
            "AND (((h.rtpPmtinfid is not null and :rtp = '1') or (h.rtpPmtinfid is null and :rtp = '0')) or :rtp is null)")
    Page<HisBatchInstr> search(@Param("begin") Date begin,
                               @Param("end") Date end,
                               @Param("aDocId") Long aDocId,
                               @Param("txid") String txid,
                               @Param("status") String status,
                               @Param("debtorName") String debtorName,
                               @Param("debtorType") String debtorType,
                               @Param("debtorAccount") String debtorAccount,
                               @Param("creditorName") String creditorName,
                               @Param("creditorType") String creditorType,
                               @Param("creditorAccount") String creditorAccount,
                               @Param("viewStatus") String viewStatus,
                               @Param("rtp") String rtp,
                               Pageable pageable);

    @Query("select h from HisBatchInstr h where " +
            "((h.processDate between :begin and :end) or :begin is null or :end is null) " +
            "AND (h.docId = :aDocId or :aDocId is null) " +
            "AND (h.mxTxid like %:txid% or :txid is null) " +
            "AND (h.status like %:status% or :status is null) " +
            "AND (h.mxDebtorName like %:debtorName% or :debtorName is null) " +
            "AND (h.mxDebtorAccountType like %:debtorType% or :debtorType is null) " +
            "AND (h.mxDebtorAccount like %:debtorAccount% or :debtorAccount is null) " +
            "AND (h.mxCreditorName like %:creditorName% or :creditorName is null) " +
            "AND (h.mxCreditorAccountType like %:creditorType% or :creditorType is null) " +
            "AND (h.mxCreditorAccount like %:creditorAccount% or :creditorAccount is null) " +
            "AND (h.viewStatus like %:viewStatus% or :viewStatus is null) " +
            "AND (((h.rtpPmtinfid is not null and :rtp = '1') or (h.rtpPmtinfid is null and :rtp = '0')) or :rtp is null)")
    List<HisBatchInstr> searchExcel(@Param("begin") Date begin,
                                    @Param("end") Date end,
                                    @Param("aDocId") Long aDocId,
                                    @Param("txid") String txid,
                                    @Param("status") String status,
                                    @Param("debtorName") String debtorName,
                                    @Param("debtorType") String debtorType,
                                    @Param("debtorAccount") String debtorAccount,
                                    @Param("creditorName") String creditorName,
                                    @Param("creditorType") String creditorType,
                                    @Param("creditorAccount") String creditorAccount,
                                    @Param("viewStatus") String viewStatus,
                                    @Param("rtp") String rtp);

    @Query("select h from HisBatchInstr h where h.docId = ?1")
    Page<HisBatchInstr> findByADocId(Long aDocId, Pageable pageable);

    @Query("select h from HisBatchInstr h where h.mxTxid = ?1")
    HisBatchInstr findByTxid(String txid);

    @Query("select h from HisBatchInstr h where h.mxOriginTxid = ?1")
    List<HisBatchInstr> getByTxid(String txid);

    @Query("""
        select new com.napas.achoffline.reportoffline.models.BatchInstrStatistic(q.mxTtc,
        q.mxDebtorAgent,h.mxMsgid,q.status, q.resultCode,count(q.docId), sum(q.mxSettlementAmount)) 
        from HisBatchPayments h, HisBatchInstr q where 
        h.processDate between :dateBegin and :dateEnd
        and (YEAR(h.processDate) = :year or :year is null) 
        and (MONTH(h.processDate) = :month or :month is null) 
        and (DAY(h.processDate) = :day or :day is null) 
        and ('findAll' in (:listBoc) or h.mxBankOpCode in (:listBoc)) 
        and ('findAll' in (:listTransType) or h.mxType in (:listTransType)) 
        and (h.mxMsgid like %:msgId% or :msgId is null)  
        and h.docId = q.docId 
        and ('findAll' in (:listTtc) or q.mxTtc in (:listTtc)) 
        and ('findAll' in (:listCreditorType) or q.mxCreditorAccountType in (:listCreditorType)) 
        and ('findAll' in (:listTransStatus) or q.status in (:listTransStatus)) 
        and ('findAll' in (:listDebtorAgent) or q.mxDebtorAgent in (:listDebtorAgent))
        group by q.mxDebtorAgent, q.mxTtc, q.status, h.mxMsgid, q.resultCode
        order by q.mxDebtorAgent, q.mxTtc, q.status, h.mxMsgid, q.resultCode                                                                             
    """)
    List<BatchInstrStatistic> findStatisticBatchInstr(
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("day") Integer day,
            @Param("listBoc") List<String> listBoc,
            @Param("listTtc") List<String> listTtc,
            @Param("listTransType") List<String> listTransType,
            @Param("listCreditorType") List<String> listCreditorType,
            @Param("listTransStatus") List<String> listTransStatus,
            @Param("listDebtorAgent") List<String> listDebtorAgent,
            @Param("msgId") String msgId,
            @Param("dateBegin") Date begin,
            @Param("dateEnd") Date end
    );

    @Query("""
        select new com.napas.achoffline.reportoffline.models.BatchInstrPaymentsStatistic(q.mxDebtorAgent,
        q.mxCreditorAgent,q.mxTtc,q.status, q.resultCode, count(q.docId), sum(q.mxSettlementAmount)) 
        from HisBatchPayments h, HisBatchInstr q where 
        h.processDate between :dateBegin and :dateEnd
        and (YEAR(h.processDate) = :year or :year is null) 
        and (MONTH(h.processDate) = :month or :month is null) 
        and (DAY(h.processDate) = :day or :day is null) 
        and ('findAll' in (:listBoc) or h.mxBankOpCode in (:listBoc)) 
        and ('findAll' in (:listTransType) or h.mxType in (:listTransType)) 
        and ('findAll' in (:listBatchStatus) or h.transStatus in (:listBatchStatus)) 
        and h.docId = q.docId 
        and ('findAll' in (:listTtc) or q.mxTtc in (:listTtc)) 
        and ('findAll' in (:listCreditorType) or q.mxCreditorAccountType in (:listCreditorType)) 
        and ('findAll' in (:listTransStatus) or q.status in (:listTransStatus)) 
        and ('findAll' in (:listDebtorAgent) or q.mxDebtorAgent in (:listDebtorAgent)) 
        and ('findAll' in (:listCreditorAgent) or q.mxCreditorAgent in (:listCreditorAgent))
        group by q.mxDebtorAgent, q.mxCreditorAgent, q.mxTtc, q.status, q.resultCode
        order by q.mxDebtorAgent, q.mxCreditorAgent, q.mxTtc, q.status, q.resultCode                                                                            
    """)
    List<BatchInstrPaymentsStatistic> findStatisticBatchInstrPayments(
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("day") Integer day,
            @Param("listBoc") List<String> listBoc,
            @Param("listTtc") List<String> listTtc,
            @Param("listTransType") List<String> listTransType,
            @Param("listCreditorType") List<String> listCreditorType,
            @Param("listBatchStatus") List<String> listBatchStatus,
            @Param("listTransStatus") List<String> listTransStatus,
            @Param("listDebtorAgent") List<String> listDebtorAgent,
            @Param("listCreditorAgent") List<String> listCreditorAgent,
            @Param("dateBegin") Date begin,
            @Param("dateEnd") Date end
    );
}
