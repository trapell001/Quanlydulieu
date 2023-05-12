/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.Payments;
import com.napas.achoffline.reportoffline.models.PaymentStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *
 * @author HuyNX
 */
@Repository
public interface PaymentsDAO extends JpaRepository<Payments, Long> {

    public Payments findByTxid(String txid);
    // sửa search đổi param

    List<Payments> findByMxOrigTxid(String txid);

    @Query(value = "SELECT * FROM HIS_PAYMENTS WHERE A_PROCESS_DATE BETWEEN :startDate AND :endDate "
            + " and MX_TXID like %:txId%\n"
            + "and (A_SESSION_ID >= :sessionId or :sessionId is null) \n"
            + "and (A_SESSION_ID <= :toSessionId or :toSessionId is null) \n"
            + "and (((A_RTP_PMTINFID is not null and :rtp = '1') or (A_RTP_PMTINFID is null and :rtp = '0')) or :rtp is null) \n"
//            + "and (A_TRANS_STATUS like %:transStatus%)\n"
            + "and (:transStatus is null or (A_TRANS_STATUS = :transStatus ) or ( :transStatus = 'Others' and A_TRANS_STATUS not in ('Posted', 'Settled', 'Rejected') ))\n"
            + "and MX_AUTH_INFO like %:authInfo%\n"
            + "and (MX_SETTLEMENT_AMOUNT >= :minAmount or :minAmount is null)\n"
            + "and (MX_SETTLEMENT_AMOUNT <= :maxAmount or :maxAmount is null)\n"
            + "and (MX_DEBTOR_AGENT like '%'||:debtorAgent||'%' or :debtorAgent is null)\n"
            + "and (MX_DEBTOR_AGENT like '%'||:debtorSystem||'%' or :debtorSystem is null)\n"
            + "and (MX_CREDITOR_AGENT like '%'||:creditorAgent||'%' or :creditorAgent is null)\n"
            + "and (MX_CREDITOR_AGENT like '%'||:creditorSystem||'%' or :creditorSystem is null)\n"
            + "and upper(MX_DEBTOR_NAME) like '%'||upper(:debtorName)||'%'\n"
            + "and upper(MX_CREDITOR_NAME) like '%'||upper(:creditorName)||'%'\n"
            + "and upper(MX_DEBTOR_ACCOUNT) like '%'||upper(:debtorAccount)||'%'\n"
            + "and upper(MX_CREDITOR_ACCOUNT) like '%'||upper(:creditorAccount)||'%'\n"
            + "and MX_DEBTOR_ACCOUNT_TYPE like %:debtorAccountType%\n"
            + "and MX_CREDITOR_ACCOUNT_TYPE like %:creditorAccountType%\n"
            + "and I_CHANNEL_ID like %:channelId%\n"
            + "and (:boc is null or (MX_BANK_OP_CODE = :boc ) or ( :boc = 'Others' and MX_BANK_OP_CODE not in ('CSDC', 'BPDC') ))\n"
            + "and (:ttc is null or (MX_TRANS_TYPE_CODE = :ttc ) or ( :ttc = 'Others' and MX_TRANS_TYPE_CODE not in ('001', '008') ))\n"
            + "and (I_MSG_TO_CREDITOR is null or upper(I_MSG_TO_CREDITOR) like '%'||upper(:msgToCreditor)||'%')\n"
            + "and MX_TYPE like %:mxType%\n"
            ,
            countQuery = "SELECT count(1) FROM HIS_PAYMENTS WHERE A_PROCESS_DATE BETWEEN :startDate AND :endDate "
                    + " and MX_TXID like %:txId%\n"
                    + "and (A_SESSION_ID >= :sessionId or :sessionId is null) \n"
                    + "and (A_SESSION_ID <= :toSessionId or :toSessionId is null) \n"
                    + "and (((A_RTP_PMTINFID is not null and :rtp = '1') or (A_RTP_PMTINFID is null and :rtp = '0')) or :rtp is null) \n"
//            + "and (A_TRANS_STATUS like %:transStatus%)\n"
                    + "and (:transStatus is null or (A_TRANS_STATUS = :transStatus ) or ( :transStatus = 'Others' and A_TRANS_STATUS not in ('Posted', 'Settled', 'Rejected') ))\n"
                    + "and MX_AUTH_INFO like %:authInfo%\n"
                    + "and (MX_SETTLEMENT_AMOUNT >= :minAmount or :minAmount is null)\n"
                    + "and (MX_SETTLEMENT_AMOUNT <= :maxAmount or :maxAmount is null)\n"
                    + "and (MX_DEBTOR_AGENT like '%'||:debtorAgent||'%' or :debtorAgent is null)\n"
                    + "and (MX_DEBTOR_AGENT like '%'||:debtorSystem||'%' or :debtorSystem is null)\n"
                    + "and (MX_CREDITOR_AGENT like '%'||:creditorAgent||'%' or :creditorAgent is null)\n"
                    + "and (MX_CREDITOR_AGENT like '%'||:creditorSystem||'%' or :creditorSystem is null)\n"
                    + "and upper(MX_DEBTOR_NAME) like '%'||upper(:debtorName)||'%'\n"
                    + "and upper(MX_CREDITOR_NAME) like '%'||upper(:creditorName)||'%'\n"
                    + "and upper(MX_DEBTOR_ACCOUNT) like '%'||upper(:debtorAccount)||'%'\n"
                    + "and upper(MX_CREDITOR_ACCOUNT) like '%'||upper(:creditorAccount)||'%'\n"
                    + "and MX_DEBTOR_ACCOUNT_TYPE like %:debtorAccountType%\n"
                    + "and MX_CREDITOR_ACCOUNT_TYPE like %:creditorAccountType%\n"
                    + "and I_CHANNEL_ID like %:channelId%\n"
                    + "and (:boc is null or (MX_BANK_OP_CODE = :boc ) or ( :boc = 'Others' and MX_BANK_OP_CODE not in ('CSDC', 'BPDC') ))\n"
                    + "and (:ttc is null or (MX_TRANS_TYPE_CODE = :ttc ) or ( :ttc = 'Others' and MX_TRANS_TYPE_CODE not in ('001', '008') ))\n"
                    + "and (I_MSG_TO_CREDITOR is null or upper(I_MSG_TO_CREDITOR) like '%'||upper(:msgToCreditor)||'%')\n"
                    + "and MX_TYPE like %:mxType%\n"
            ,
            nativeQuery = true)
    public Page<Payments> searchPayments(Pageable sort,
                                         @Param("startDate") Date beginDate,
                                         @Param("endDate") Date endDate,
                                         @Param("sessionId") String sessionId,
                                         @Param("toSessionId") String toSessionId,
                                         @Param("mxType") String mxType,
                                         @Param("txId") String msgId,
                                         @Param("transStatus") String transStatus,
                                         @Param("authInfo") String authInfo,
                                         @Param("minAmount") String minAmount,
                                         @Param("maxAmount") String maxAmount,
                                         @Param("debtorAgent") String debitBank,
                                         @Param("creditorAgent") String creditBank,
                                         @Param("debtorName") String deptorName,
                                         @Param("creditorName") String creditorName,
                                         @Param("debtorAccount") String deptorAccount,
                                         @Param("creditorAccount") String creditorAccount,
                                         @Param("debtorAccountType") String deptorAccountType,
                                         @Param("creditorAccountType") String creditorAccountType,
                                         @Param("channelId") String channelId,
                                         @Param("boc") String boc,
                                         @Param("ttc") String ttc,
                                         @Param("msgToCreditor") String msgToCreditor,
                                         @Param("debtorSystem") String debtorSystem,
                                         @Param("creditorSystem") String creditor,
                                         @Param("rtp") String rtp
    );

    @Query(value = "select * from HIS_PAYMENTS where " +
            "(MX_CREDITOR_AGENT like %?1% or ?1 is null) " +
            "AND (A_SESSION_ID in ?2) " +
            "AND (MX_AUTH_INFO = 'NAUT') " +
            "AND (HAVE_RETURN = 0) " +
            "AND (A_TRANS_STATUS = 'Settled' OR A_TRANS_STATUS = 'Posted')"
            ,nativeQuery = true)
    List<Payments> getPaymentsWarning(String tctv,List<Long> sessionID);

    @Query(value = """
        select new com.napas.achoffline.reportoffline.models.PaymentStatistic(debtorAgent,
        creditorAgent, transTypeCode, type, channelId, mxCreditorAccountType, transStatus, 
        authInfo, achResultCode, partyResultCode,count(debtorAgent), sum(settlementAmount), lateRespond) 
        from Payments where 
        processDate between :dateBegin and :dateEnd
        and (YEAR(processDate) = :year or :year is null)
        and (MONTH(processDate) = :month or :month is null)
        and (DAY(processDate) = :day or :day is null)
        and(:debtorSystem = substring(debtorAgent,5,4) or :debtorSystem is null)
        and(:creditorSystem = substring(creditorAgent,5,4) or :creditorSystem is null)
        and ('findAll' in (:listBoc) or mxBankOpCode in (:listBoc))
        and ('findAll' in (:listTtc) or transTypeCode in (:listTtc))
        and ('findAll' in (:listTransType) or type in (:listTransType))
        and ('findAll' in (:listCreditorType) or mxCreditorAccountType in (:listCreditorType))
        and ('findAll' in (:listStatus) or transStatus in (:listStatus))
        and ('findAll' in (:listAuth) or authInfo in (:listAuth))
        and ('findAll' in (:listChannel) or channelId in (:listChannel))
        and ('findAll' in (:listInstructing) or substring(debtorAgent,1,4) in (:listInstructing))
        and ('findAll' in (:listInstructed) or substring(creditorAgent,1,4) in (:listInstructed))
        group by debtorAgent, creditorAgent, transTypeCode, type, channelId, mxCreditorAccountType, 
        transStatus, authInfo, achResultCode, partyResultCode, lateRespond
        order by debtorAgent, creditorAgent, transTypeCode, type, channelId, mxCreditorAccountType, 
        transStatus, authInfo
    """)
    List<PaymentStatistic> findPayment(
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("day") Integer day,
            @Param("debtorSystem") String debtorSystem,
            @Param("creditorSystem") String creditorSystem,
            @Param("listBoc") List<String> listBoc,
            @Param("listTtc") List<String> listTtc,
            @Param("listTransType") List<String> listTransType,
            @Param("listCreditorType") List<String> listCreditorType,
            @Param("listStatus") List<String> listStatus,
            @Param("listAuth") List<String> listAuth,
            @Param("listChannel") List<String> listChannel,
            @Param("listInstructing") List<String> listInstructing,
            @Param("listInstructed") List<String> listInstructed,
            @Param("dateBegin") Date begin,
            @Param("dateEnd") Date end
    );
}
