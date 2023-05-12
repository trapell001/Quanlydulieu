/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import com.napas.achoffline.reportoffline.define.TariffType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * @author huynx
 */
@Entity
@Table(name = "HIS_NRT_PAYMENTS_FEE")
@NamedQueries({
    @NamedQuery(name = "HisNrtPaymentsFee.findAll", query = "SELECT h FROM HisNrtPaymentsFee h")})
public class HisNrtPaymentsFee implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "PROCESS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processDate;
    @Basic(optional = false)
    @NotNull()
    @Column(name = "CREATION_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SESSION_ID")
    private long sessionId;
    @Size(max = 50)
    @Column(name = "TXID")
    private String txid;
    @Size(max = 50)
    @Column(name = "INSTR_ID")
    private String instrId;
    @Size(max = 50)
    @Column(name = "END_TO_END_ID")
    private String endToEndId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull()
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 20)
    @Column(name = "DEBTOR_AGENT")
    private String debtorAgent;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CREDITOR_AGENT")
    private String creditorAgent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MSG_TYPE")
    private String msgType;
    @Size(max = 20)
    @Column(name = "TTC")
    private String ttc;
    @Size(max = 20)
    @Column(name = "CHANNEL_ID")
    private String channelId;
    @Basic(optional = false)
    @NotNull()
    @Column(name = "FEE_IRF_ISS")
    private BigDecimal feeIrfIss;
    @Basic(optional = false)
    @NotNull()
    @Column(name = "FEE_SVF_ISS")
    private BigDecimal feeSvfIss;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FEE_IRF_RCV")
    private BigDecimal feeIrfRcv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FEE_SVF_RCV")
    private BigDecimal feeSvfRcv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALCULATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date calculatedDate;
    @Size(max = 50)
    @Column(name = "ORIGINAL_TXID")
    private String originalTxid;

    @Column(name = "TARIFF_TYPE")
    @Enumerated(EnumType.STRING)
    private TariffType tariffType;
    @Column(name = "TARIFF_WITHOUT_LADDER_ID")
    private Integer tariffWithoutLadderId;
    @Column(name = "SPECIAL_CHANNEL_FLAG")
    private Integer specialChannelFlag;
    @Column(name = "SPECIAL_BANK_DEST_FLAG")
    private Integer specialBankDestFlag;
    @Size(max = 50)
    @Column(name = "ORIGINAL_TXID_FOR_RETURN")
    private String originalTxidForReturn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "BOC")
    private String boc;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOC_ID")
    private Long docId;
    @Column(name = "TARIFF_ID")
    private Integer tariffId;
    @Column(name = "ADDED_PAYMENT_FLAG")
    private Short addedPaymentFlag;
    @Column(name = "SPECIAL_BANK_ACCOUNT_FLAG")
    private Short specialBankAccountFlag;
    @Column(name = "MONTH_ORDER")
    private Long monthOrder;
    @Column(name = "TARIFF_ID_FOR_BEN")
    private Integer tariffIdForBen;

    public HisNrtPaymentsFee() {
    }

    public HisNrtPaymentsFee(Long docId) {
        this.docId = docId;
    }

    public HisNrtPaymentsFee(Date processDate, Date creationDateTime, long sessionId, String txid, String instrId, String endToEndId, BigDecimal amount, String debtorAgent, String creditorAgent, Date valueDate, String msgType, String ttc, String channelId, BigDecimal feeIrfIss, BigDecimal feeSvfIss, BigDecimal feeIrfRcv, BigDecimal feeSvfRcv, Date calculatedDate, String originalTxid, TariffType tariffType, Integer tariffWithoutLadderId, Integer specialChannelFlag, Integer specialBankDestFlag, String originalTxidForReturn, String boc, Long docId, Integer tariffId, Short addedPaymentFlag, Short specialBankAccountFlag, Long monthOrder, Integer tariffIdForBen) {
        this.processDate = processDate;
        this.creationDateTime = creationDateTime;
        this.sessionId = sessionId;
        this.txid = txid;
        this.instrId = instrId;
        this.endToEndId = endToEndId;
        this.amount = amount;
        this.debtorAgent = debtorAgent;
        this.creditorAgent = creditorAgent;
        this.valueDate = valueDate;
        this.msgType = msgType;
        this.ttc = ttc;
        this.channelId = channelId;
        this.feeIrfIss = feeIrfIss;
        this.feeSvfIss = feeSvfIss;
        this.feeIrfRcv = feeIrfRcv;
        this.feeSvfRcv = feeSvfRcv;
        this.calculatedDate = calculatedDate;
        this.originalTxid = originalTxid;
        this.tariffType = tariffType;
        this.tariffWithoutLadderId = tariffWithoutLadderId;
        this.specialChannelFlag = specialChannelFlag;
        this.specialBankDestFlag = specialBankDestFlag;
        this.originalTxidForReturn = originalTxidForReturn;
        this.boc = boc;
        this.docId = docId;
        this.tariffId = tariffId;
        this.addedPaymentFlag = addedPaymentFlag;
        this.specialBankAccountFlag = specialBankAccountFlag;
        this.monthOrder = monthOrder;
        this.tariffIdForBen = tariffIdForBen;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }

    public Short getAddedPaymentFlag() {
        return addedPaymentFlag;
    }

    public void setAddedPaymentFlag(Short addedPaymentFlag) {
        this.addedPaymentFlag = addedPaymentFlag;
    }

    public Short getSpecialBankAccountFlag() {
        return specialBankAccountFlag;
    }

    public void setSpecialBankAccountFlag(Short specialBankAccountFlag) {
        this.specialBankAccountFlag = specialBankAccountFlag;
    }

    public Long getMonthOrder() {
        return monthOrder;
    }

    public void setMonthOrder(Long monthOrder) {
        this.monthOrder = monthOrder;
    }

    public Integer getTariffIdForBen() {
        return tariffIdForBen;
    }

    public void setTariffIdForBen(Integer tariffIdForBen) {
        this.tariffIdForBen = tariffIdForBen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docId != null ? docId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HisNrtPaymentsFee)) {
            return false;
        }
        HisNrtPaymentsFee other = (HisNrtPaymentsFee) object;
        if ((this.docId == null && other.docId != null) || (this.docId != null && !this.docId.equals(other.docId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.models.HisNrtPaymentsFee[ docId=" + docId + " ]";
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public Date getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getInstrId() {
        return instrId;
    }

    public void setInstrId(String instrId) {
        this.instrId = instrId;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDebtorAgent() {
        return debtorAgent;
    }

    public void setDebtorAgent(String debtorAgent) {
        this.debtorAgent = debtorAgent;
    }

    public String getCreditorAgent() {
        return creditorAgent;
    }

    public void setCreditorAgent(String creditorAgent) {
        this.creditorAgent = creditorAgent;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getTtc() {
        return ttc;
    }

    public void setTtc(String ttc) {
        this.ttc = ttc;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public BigDecimal getFeeIrfIss() {
        return feeIrfIss;
    }

    public void setFeeIrfIss(BigDecimal feeIrfIss) {
        this.feeIrfIss = feeIrfIss;
    }

    public BigDecimal getFeeSvfIss() {
        return feeSvfIss;
    }

    public void setFeeSvfIss(BigDecimal feeSvfIss) {
        this.feeSvfIss = feeSvfIss;
    }

    public BigDecimal getFeeIrfRcv() {
        return feeIrfRcv;
    }

    public void setFeeIrfRcv(BigDecimal feeIrfRcv) {
        this.feeIrfRcv = feeIrfRcv;
    }

    public BigDecimal getFeeSvfRcv() {
        return feeSvfRcv;
    }

    public void setFeeSvfRcv(BigDecimal feeSvfRcv) {
        this.feeSvfRcv = feeSvfRcv;
    }

    public Date getCalculatedDate() {
        return calculatedDate;
    }

    public void setCalculatedDate(Date calculatedDate) {
        this.calculatedDate = calculatedDate;
    }

    public String getOriginalTxid() {
        return originalTxid;
    }

    public void setOriginalTxid(String originalTxid) {
        this.originalTxid = originalTxid;
    }

    public TariffType getTariffType() {
        return tariffType;
    }

    public void setTariffType(TariffType tariffType) {
        this.tariffType = tariffType;
    }

    public Integer getTariffWithoutLadderId() {
        return tariffWithoutLadderId;
    }

    public void setTariffWithoutLadderId(Integer tariffWithoutLadderId) {
        this.tariffWithoutLadderId = tariffWithoutLadderId;
    }

    public Integer getSpecialChannelFlag() {
        return specialChannelFlag;
    }

    public void setSpecialChannelFlag(Integer specialChannelFlag) {
        this.specialChannelFlag = specialChannelFlag;
    }

    public Integer getSpecialBankDestFlag() {
        return specialBankDestFlag;
    }

    public void setSpecialBankDestFlag(Integer specialBankDestFlag) {
        this.specialBankDestFlag = specialBankDestFlag;
    }

    public String getOriginalTxidForReturn() {
        return originalTxidForReturn;
    }

    public void setOriginalTxidForReturn(String originalTxidForReturn) {
        this.originalTxidForReturn = originalTxidForReturn;
    }

    public String getBoc() {
        return boc;
    }

    public void setBoc(String boc) {
        this.boc = boc;
    }

}
