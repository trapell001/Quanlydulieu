/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author HuyNX
 */
@Entity
@Table(name = "HIS_PAYMENTS")
@NamedQueries({
    @NamedQuery(name = "Payments.findAll", query = "SELECT p FROM Payments p")})
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "A_PROCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;

    @Column(name = "A_RESPOND_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date respondDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "A_MODIF_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "A_FIRST_SYNC_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aFirstSyncDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "A_LAST_SYNC_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aLastSyncDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "MX_TRANS_DATE", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime transDate;

    @Size(max = 10)
    @Column(name = "I_TDT")
    private String iTDT;

    @Column(name = "A_SESSION_ID")
    private Long sessionId;

    @Column(name = "A_TRANS_ID")
    private Long transId;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "A_DOC_ID")
    private Long docId;

    @Column(name = "A_STEP_ID")
    private Long aStepId;

    @Column(name = "A_PACK_ID")
    private Long aPackId;

    @Size(max = 35)
    @Column(name = "MX_MSGID")
    private String msgID;
    
    @Size(max = 35)
    @Column(name = "MX_ORIG_TXID")
    private String mxOrigTxid;

    @Size(max = 35)
    @Column(name = "MX_ORIG_MSGID")
    private String origMsgID;

    @Size(max = 35)
    @Column(name = "MX_TXID")
    private String txid;

    @Size(max = 15)
    @Column(name = "MX_TYPE")
    private String type;

    @Size(max = 35)
    @Column(name = "MX_BANK_OP_CODE")
    private String mxBankOpCode;

    @Size(max = 2)
    @Column(name = "I_PAYMENT_TYPE")
    private String paymentType;

    @Size(max = 2)
    @Column(name = "I_CHANNEL_ID")
    private String channelId;

    @Size(max = 6)
    @Column(name = "I_SERVICE_CODE")
    private String serviceCode;

    @Size(max = 20)
    @Column(name = "MX_TRANS_TYPE_CODE")
    private String transTypeCode;

    @Size(max = 4)
    @Column(name = "I_MERCHANT_TYPE")
    private String merchantType;

    @Size(max = 3)
    @Column(name = "I_PEM")
    private String posEntryMode;

    @Size(max = 2)
    @Column(name = "I_PCD")
    private String posCondition;

    @Column(name = "MX_PRIORITY")
    private Long priority;

    @Size(max = 20)
    @Column(name = "A_TRANS_STATUS")
    private String transStatus;

    @Size(max = 20)
    @Column(name = "MX_AUTH_INFO")
    private String authInfo;

    @Column(name = "MX_TTL_SETTLEMENT_AMOUNT")
    private BigDecimal ttlSettlementAmount;

    @Column(name = "MX_SETTLEMENT_AMOUNT")
    private BigDecimal settlementAmount;

    @Size(max = 3)
    @Column(name = "MX_SETTLEMENT_CURRENCY")
    private String settlementCurrency;

    @Size(max = 12)
    @Column(name = "I_TRANSACTION_AMOUNT")
    private String transactionAmount;

    @Size(max = 3)
    @Column(name = "I_TRANSACTION_CURRENCY")
    private String transactionCurrency;

    @Size(max = 3)
    @Column(name = "I_AIC")
    private String iAIC;

    @Size(max = 8)
    @Column(name = "I_SCR")
    private String iSCR;

    @Size(max = 8)
    @Column(name = "MX_INSTRUCTING_AGENT")
    private String instructingAgent;

    @Size(max = 8)
    @Column(name = "MX_DEBTOR_AGENT")
    private String debtorAgent;

    @Size(max = 6)
    @Column(name = "I_FID")
    private String iFID;

    @Size(max = 1024)
    @Column(name = "MX_DEBTOR_NAME")
    private String debtorName;

    @Size(max = 34)
    @Column(name = "MX_DEBTOR_ACCOUNT")
    private String debtorAccount;

    @Size(max = 28)
    @Column(name = "I_FAI")
    private String iFAI;

    @Size(max = 3)
    @Column(name = "MX_DEBTOR_ACCOUNT_TYPE")
    private String mxDebtorAccountType;

    @Size(max = 2048)
    @Column(name = "MX_DEBTOR_ADRLINE")
    private String debtorAdrline;
    @Size(max = 8)
    @Column(name = "MX_INSTRUCTED_AGENT")
    private String instructedAgent;

    @Size(max = 8)
    @Column(name = "MX_CREDITOR_AGENT")
    private String creditorAgent;

    @Size(max = 6)
    @Column(name = "I_BID")
    private String iBID;

    @Size(max = 1024)
    @Column(name = "MX_CREDITOR_NAME")
    private String creditorName;

    @Size(max = 34)
    @Column(name = "MX_CREDITOR_ACCOUNT")
    private String creditorAccount;

    @Size(max = 28)
    @Column(name = "I_TAI")
    private String iTAI;

    @Size(max = 3)
    @Column(name = "MX_CREDITOR_ACCOUNT_TYPE")
    private String mxCreditorAccountType;

    @Size(max = 2048)
    @Column(name = "MX_CREDITOR_ADRLINE")
    private String creditorAdrline;

    @Size(max = 210)
    @Column(name = "I_MSG_TO_CREDITOR")
    private String msgToCreditor;

    @Size(max = 40)
    @Column(name = "I_MERCHANT_NAME")
    private String merchantName;

    @Size(max = 6)
    @Column(name = "I_SYSTEM_TRACE")
    private String systemTrace;

    @Size(max = 8)
    @Column(name = "I_TERM_ID")
    private String termID;

    @Size(max = 15)
    @Column(name = "I_MERCHANT_ID")
    private String merchantID;

    @Size(max = 12)
    @Column(name = "I_TRXN_REF")
    private String txrnRef;

    @Size(max = 16)
    @Column(name = "I_TRANS_REF_NUM")
    private String transRefNum;

    @Size(max = 35)
    @Column(name = "MX_INSTRID")
    private String instrid;

    @Size(max = 35)
    @Column(name = "MX_ENDTOENDID")
    private String endtoendid;

    @Size(max = 20)
    @Column(name = "MX_TTC")
    private String mxTTC;

    @Column(name = "A_OPERDAY_ID")
    private Integer aOperdayId;

    @Column(name = "I_AIR")
    private String air;

    @Column(name = "MX_ACH_RESULT_CODE")
    private String achResultCode;

    @Column(name = "MX_PARTY_RESULT_CODE")
    private String partyResultCode;

    @Column(name = "I_DEBTOR_BANK_ID")
    private String debtorBankId;

    @Column(name = "HAVE_RETURN")
    private Integer haveReturn;

    @Column(name = "I_SETT_YEAR")
    private String settYear;

    @Column(name = "A_RTP_PMTINFID")
    private String rtpPmtinfid;

    @Column(name = "A_RTP_ISSUER")
    private String rtpIssuer;

    @Column(name = "B_BUSINESS_SVC_CODE")
    private String businessSvcCode;

    @Column(name = "MX_VALUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date mxValueDate;

    @Column(name = "I_SETT_DATE")
    private String settDate;

    @Column(name = "I_RC")
    private String rc;

    @Column(name = "MX_ORIG_VALUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date mxOrigValueDate;

    @Column(name = "MX_PARTY_RESULT_DETAIL")
    private String mxPartyResultDetail;

    @Column(name = "MX_RETURN_REASON")
    private String mxReturnReason;

    @Column(name = "MX_RETURN_REASON_DETAIL")
    private String mxReturnReasonDetail;

    @Column(name = "A_SYSTEM_TO_SYSTEM")
    private String systemToSystem;

    @Column(name = "LATE_RESPOND")
    private Long lateRespond;

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public Date getModifDate() {
        return modifDate;
    }

    public void setModifDate(Date modifDate) {
        this.modifDate = modifDate;
    }

    public Date getaFirstSyncDate() {
        return aFirstSyncDate;
    }

    public void setaFirstSyncDate(Date aFirstSyncDate) {
        this.aFirstSyncDate = aFirstSyncDate;
    }

    public Date getaLastSyncDate() {
        return aLastSyncDate;
    }

    public void setaLastSyncDate(Date aLastSyncDate) {
        this.aLastSyncDate = aLastSyncDate;
    }

    public OffsetDateTime getTransDate() {
        return transDate;
    }

    public void setTransDate(OffsetDateTime transDate) {
        this.transDate = transDate;
    }

    public String getiTDT() {
        return iTDT;
    }

    public void setiTDT(String iTDT) {
        this.iTDT = iTDT;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Long getaStepId() {
        return aStepId;
    }

    public void setaStepId(Long aStepId) {
        this.aStepId = aStepId;
    }

    public Long getaPackId() {
        return aPackId;
    }

    public void setaPackId(Long aPackId) {
        this.aPackId = aPackId;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMxBankOpCode() {
        return mxBankOpCode;
    }

    public void setMxBankOpCode(String mxBankOpCode) {
        this.mxBankOpCode = mxBankOpCode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getTransTypeCode() {
        return transTypeCode;
    }

    public void setTransTypeCode(String transTypeCode) {
        this.transTypeCode = transTypeCode;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getPosEntryMode() {
        return posEntryMode;
    }

    public void setPosEntryMode(String posEntryMode) {
        this.posEntryMode = posEntryMode;
    }

    public String getPosCondition() {
        return posCondition;
    }

    public void setPosCondition(String posCondition) {
        this.posCondition = posCondition;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    public BigDecimal getTtlSettlementAmount() {
        return ttlSettlementAmount;
    }

    public void setTtlSettlementAmount(BigDecimal ttlSettlementAmount) {
        this.ttlSettlementAmount = ttlSettlementAmount;
    }

    public BigDecimal getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(BigDecimal settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(String settlementCurrency) {
        this.settlementCurrency = settlementCurrency;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getiAIC() {
        return iAIC;
    }

    public void setiAIC(String iAIC) {
        this.iAIC = iAIC;
    }

    public String getiSCR() {
        return iSCR;
    }

    public void setiSCR(String iSCR) {
        this.iSCR = iSCR;
    }

    public String getInstructingAgent() {
        return instructingAgent;
    }

    public void setInstructingAgent(String instructingAgent) {
        this.instructingAgent = instructingAgent;
    }

    public String getDebtorAgent() {
        return debtorAgent;
    }

    public void setDebtorAgent(String debtorAgent) {
        this.debtorAgent = debtorAgent;
    }

    public String getiFID() {
        return iFID;
    }

    public void setiFID(String iFID) {
        this.iFID = iFID;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(String debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public String getiFAI() {
        return iFAI;
    }

    public void setiFAI(String iFAI) {
        this.iFAI = iFAI;
    }

    public String getMxDebtorAccountType() {
        return mxDebtorAccountType;
    }

    public void setMxDebtorAccountType(String mxDebtorAccountType) {
        this.mxDebtorAccountType = mxDebtorAccountType;
    }

    public String getDebtorAdrline() {
        return debtorAdrline;
    }

    public void setDebtorAdrline(String debtorAdrline) {
        this.debtorAdrline = debtorAdrline;
    }

    public String getInstructedAgent() {
        return instructedAgent;
    }

    public void setInstructedAgent(String instructedAgent) {
        this.instructedAgent = instructedAgent;
    }

    public String getCreditorAgent() {
        return creditorAgent;
    }

    public void setCreditorAgent(String creditorAgent) {
        this.creditorAgent = creditorAgent;
    }

    public String getiBID() {
        return iBID;
    }

    public void setiBID(String iBID) {
        this.iBID = iBID;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(String creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public String getiTAI() {
        return iTAI;
    }

    public void setiTAI(String iTAI) {
        this.iTAI = iTAI;
    }

    public String getMxCreditorAccountType() {
        return mxCreditorAccountType;
    }

    public void setMxCreditorAccountType(String mxCreditorAccountType) {
        this.mxCreditorAccountType = mxCreditorAccountType;
    }

    public String getCreditorAdrline() {
        return creditorAdrline;
    }

    public void setCreditorAdrline(String creditorAdrline) {
        this.creditorAdrline = creditorAdrline;
    }

    public String getMsgToCreditor() {
        return msgToCreditor;
    }

    public void setMsgToCreditor(String msgToCreditor) {
        this.msgToCreditor = msgToCreditor;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSystemTrace() {
        return systemTrace;
    }

    public void setSystemTrace(String systemTrace) {
        this.systemTrace = systemTrace;
    }

    public String getTermID() {
        return termID;
    }

    public void setTermID(String termID) {
        this.termID = termID;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getTxrnRef() {
        return txrnRef;
    }

    public void setTxrnRef(String txrnRef) {
        this.txrnRef = txrnRef;
    }

    public String getTransRefNum() {
        return transRefNum;
    }

    public void setTransRefNum(String transRefNum) {
        this.transRefNum = transRefNum;
    }

    public String getInstrid() {
        return instrid;
    }

    public void setInstrid(String instrid) {
        this.instrid = instrid;
    }

    public String getEndtoendid() {
        return endtoendid;
    }

    public void setEndtoendid(String endtoendid) {
        this.endtoendid = endtoendid;
    }

    public String getMxTTC() {
        return mxTTC;
    }

    public void setMxTTC(String mxTTC) {
        this.mxTTC = mxTTC;
    }

    public Integer getaOperdayId() {
        return aOperdayId;
    }

    public void setaOperdayId(Integer aOperdayId) {
        this.aOperdayId = aOperdayId;
    }

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
    }

    public String getAchResultCode() {
        return achResultCode;
    }

    public void setAchResultCode(String achResultCode) {
        this.achResultCode = achResultCode;
    }

    public String getPartyResultCode() {
        return partyResultCode;
    }

    public void setPartyResultCode(String partyResultCode) {
        this.partyResultCode = partyResultCode;
    }

    public String getDebtorBankId() {
        return debtorBankId;
    }

    public void setDebtorBankId(String debtorBankId) {
        this.debtorBankId = debtorBankId;
    }

    public Date getRespondDate() {
        return respondDate;
    }

    public void setRespondDate(Date respondDate) {
        this.respondDate = respondDate;
    }

    public String getOrigMsgID() {
        return origMsgID;
    }

    public void setOrigMsgID(String origMsgID) {
        this.origMsgID = origMsgID;
    }

    public Integer getHaveReturn() {
        return haveReturn;
    }

    public void setHaveReturn(Integer haveReturn) {
        this.haveReturn = haveReturn;
    }

    public String getSettYear() {
        return settYear;
    }

    public void setSettYear(String settYear) {
        this.settYear = settYear;
    }

    public String getRtpPmtinfid() {
        return rtpPmtinfid;
    }

    public void setRtpPmtinfid(String rtpPmtinfid) {
        this.rtpPmtinfid = rtpPmtinfid;
    }

    public String getRtpIssuer() {
        return rtpIssuer;
    }

    public void setRtpIssuer(String rtpIssuer) {
        this.rtpIssuer = rtpIssuer;
    }

    public String getBusinessSvcCode() {
        return businessSvcCode;
    }

    public void setBusinessSvcCode(String businessSvcCode) {
        this.businessSvcCode = businessSvcCode;
    }

    public String getMxOrigTxid() {
        return mxOrigTxid;
    }

    public void setMxOrigTxid(String mxOrigTxid) {
        this.mxOrigTxid = mxOrigTxid;
    }

    public Date getMxValueDate() {
        return mxValueDate;
    }

    public void setMxValueDate(Date mxValueDate) {
        this.mxValueDate = mxValueDate;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public Date getMxOrigValueDate() {
        return mxOrigValueDate;
    }

    public void setMxOrigValueDate(Date mxOrigValueDate) {
        this.mxOrigValueDate = mxOrigValueDate;
    }

    public String getMxPartyResultDetail() {
        return mxPartyResultDetail;
    }

    public void setMxPartyResultDetail(String mxPartyResultDetail) {
        this.mxPartyResultDetail = mxPartyResultDetail;
    }

    public String getMxReturnReason() {
        return mxReturnReason;
    }

    public void setMxReturnReason(String mxReturnReason) {
        this.mxReturnReason = mxReturnReason;
    }

    public String getMxReturnReasonDetail() {
        return mxReturnReasonDetail;
    }

    public void setMxReturnReasonDetail(String mxReturnReasonDetail) {
        this.mxReturnReasonDetail = mxReturnReasonDetail;
    }

    public String getSystemToSystem() {
        return systemToSystem;
    }

    public void setSystemToSystem(String systemToSystem) {
        this.systemToSystem = systemToSystem;
    }
}
