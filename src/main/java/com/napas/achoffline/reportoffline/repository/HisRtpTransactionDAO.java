package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.HisRtpTransaction;
import com.napas.achoffline.reportoffline.models.RptTransactionStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface HisRtpTransactionDAO extends JpaRepository<HisRtpTransaction, Long> {
    @Query("""
        select distinct h from HisRtpTransaction h where 
        ((h.processDate between :beginDate and :endDate) or :beginDate is null or :endDate is null) 
        and (h.mxTxid like %:txid% or :txid is null)
        and (h.paymentInfoId like %:msgIdRtp% or :msgIdRtp is null) 
        and (h.rtpServiceType like %:rtpType% or :rtpType is null) 
        and (h.sessionId >= :sessionFrom or :sessionFrom is null) 
        and (h.sessionId <= :sessionTo or :sessionTo is null) 
        and (h.transStatus like %:transStatus% or :transStatus is null) 
        and (h.bankOperationCode like %:boc% or :boc is null) 
        and (substring(h.initiatingParty,1,4) like %:initiatingParty% or :initiatingParty is null) 
        and (substring(h.forwardingAgent,1,4) like %:debtorAgent% or :debtorAgent is null) 
        and (substring(h.initiatingParty,1,4) like %:creditorAgent% or :creditorAgent is null) 
        and (substring(h.initiatingParty,5,4) like %:initiatingSystem% or :initiatingSystem is null)
        and (h.debtorName like %:debtorName% or :debtorName is null) 
        and (h.debtorAccount like %:debtorAccount% or :debtorAccount is null) 
        and (h.creditorName like %:creditorName% or :creditorName is null) 
        and (h.creditorAccount like %:creditorAccount% or :creditorAccount is null) 
    """)
    public Page<HisRtpTransaction> searchHisRpt(@Param("beginDate") Date beginDate,
                                                @Param("endDate") Date endDate,
                                                @Param("msgIdRtp") String msgIdRtp,
                                                @Param("rtpType") String rtpType,
                                                @Param("sessionFrom") Long sessionFrom,
                                                @Param("sessionTo") Long sessionTo,
                                                @Param("transStatus") String transStatus,
                                                @Param("boc") String boc,
                                                @Param("initiatingParty") String initiatingParty,
                                                @Param("debtorAgent") String debtorAgent,
                                                @Param("creditorAgent") String creditorAgent,
                                                @Param("debtorName") String debtorName,
                                                @Param("debtorAccount") String debtorAccount,
                                                @Param("creditorName") String creditorName,
                                                @Param("creditorAccount") String creditorAccount,
                                                @Param("txid") String txid,
                                                @Param("initiatingSystem") String initiatingSystem,
                                                Pageable pageable);

    @Query("""
        select distinct h from HisRtpTransaction h where 
        ((h.processDate between :beginDate and :endDate) or :beginDate is null or :endDate is null) 
        and (h.mxTxid like %:txid% or :txid is null)
        and (h.paymentInfoId like %:msgIdRtp% or :msgIdRtp is null) 
        and (h.rtpServiceType like %:rtpType% or :rtpType is null) 
        and (h.sessionId >= :sessionFrom or :sessionFrom is null) 
        and (h.sessionId <= :sessionTo or :sessionTo is null) 
        and (h.transStatus like %:transStatus% or :transStatus is null) 
        and (h.bankOperationCode like %:boc% or :boc is null) 
        and (substring(h.initiatingParty,1,4) like %:initiatingParty% or :initiatingParty is null) 
        and (substring(h.forwardingAgent,1,4) like %:debtorAgent% or :debtorAgent is null) 
        and (substring(h.initiatingParty,1,4) like %:creditorAgent% or :creditorAgent is null) 
        and (substring(h.initiatingParty,5,4) like %:initiatingSystem% or :initiatingSystem is null) 
        and (h.debtorName like %:debtorName% or :debtorName is null) 
        and (h.debtorAccount like %:debtorAccount% or :debtorAccount is null) 
        and (h.creditorName like %:creditorName% or :creditorName is null) 
        and (h.creditorAccount like %:creditorAccount% or :creditorAccount is null)  
    """)
    public List<HisRtpTransaction> export(@Param("beginDate") Date beginDate,
                                          @Param("endDate") Date endDate,
                                          @Param("msgIdRtp") String msgIdRtp,
                                          @Param("rtpType") String rtpType,
                                          @Param("sessionFrom") Long sessionFrom,
                                          @Param("sessionTo") Long sessionTo,
                                          @Param("transStatus") String transStatus,
                                          @Param("boc") String boc,
                                          @Param("initiatingParty") String initiatingParty,
                                          @Param("debtorAgent") String debtorAgent,
                                          @Param("creditorAgent") String creditorAgent,
                                          @Param("debtorName") String debtorName,
                                          @Param("debtorAccount") String debtorAccount,
                                          @Param("creditorName") String creditorName,
                                          @Param("creditorAccount") String creditorAccount,
                                          @Param("txid") String txid,
                                          @Param("initiatingSystem") String initiatingSystem);

    @Query("""
        select new com.napas.achoffline.reportoffline.models.RptTransactionStatistic(rtpServiceType, ttc, 
        initiatingParty, forwardingAgent,initiatingParty, transStatus, count(id), respondCode)
        from HisRtpTransaction where 
        processDate between :dateBegin and :dateEnd 
        and (YEAR(processDate) = :year or :year is null) 
        and (MONTH(processDate) = :month or :month is null) 
        and (DAY(processDate) = :day or :day is null) 
        and ('findAll' in (:listBoc) or bankOperationCode in (:listBoc)) 
        and ('findAll' in (:listTtc) or ttc in (:listTtc)) 
        and ('findAll' in (:listRtpType) or rtpServiceType in (:listRtpType)) 
        and ('findAll' in (:listInitiatingParty) or substring(initiatingParty,1,4) in (:listInitiatingParty)) 
        and ('findAll' in (:listDebtorAgent) or  substring(forwardingAgent,1,4) in (:listDebtorAgent)) 
        and ('findAll' in (:listCreditorAgent) or substring(initiatingParty,1,4) in (:listCreditorAgent)) 
        and (substring(initiatingParty,5,4) like %:initiatingSystem% or :initiatingSystem is null)
        and ('findAll' in (:listTransStatus) or transStatus in (:listTransStatus)) 
        group by rtpServiceType, ttc, initiatingParty, forwardingAgent, transStatus, respondCode
        order by initiatingParty, forwardingAgent, transStatus, rtpServiceType, ttc
    """)
    List<RptTransactionStatistic> findStatisticRtpr(@Param("year") Integer year,
                                                    @Param("month") Integer month,
                                                    @Param("day") Integer day,
                                                    @Param("listBoc") List<String> listBoc,
                                                    @Param("listTtc") List<String> listTtc,
                                                    @Param("listRtpType") List<String> listRtpType,
                                                    @Param("listInitiatingParty") List<String> listInitiatingParty,
                                                    @Param("listDebtorAgent") List<String> listDebtorAgent,
                                                    @Param("listCreditorAgent") List<String> listCreditorAgent,
                                                    @Param("listTransStatus") List<String> listTransStatus,
                                                    @Param("initiatingSystem") String initiatingSystem,
                                                    @Param("dateBegin") Date begin,
                                                    @Param("dateEnd") Date end);
}
