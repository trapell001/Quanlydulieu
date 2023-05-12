/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
@Table(name = "TBL_TARIFF_WITHOUT_LADDER")
@NamedQueries({
    @NamedQuery(name = "TblTariffWithoutLadder.findAll", query = "SELECT t FROM TblTariffWithoutLadder t")})
public class TblTariffWithoutLadder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TARIFF")
    @SequenceGenerator(sequenceName = "SEQ_TARIFF", allocationSize = 1, name = "SEQ_TARIFF")
    @Column(name = "TARIFF_ID")
    private Integer tariffId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_PLAN_ID")
    private int tariffPlanId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SERVICE")
    private String service;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "BOC")
    private String boc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TTC")
    private String ttc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CHANNEL")
    private String channel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PAYMENT_CASE")
    private String paymentCase;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALUE_RANGE_MIN")
    private long valueRangeMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALUE_RANGE_MAX")
    private long valueRangeMax;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "SENDER_INTERCHANGE_FEE")
    private BigDecimal senderInterchangeFee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "SENDER_INTERCHANGE_FEE_TYPE")
    private String senderInterchangeFeeType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECEIVER_INTERCHANGE_FEE")
    private BigDecimal receiverInterchangeFee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "RECEIVER_INTERCHANGE_FEE_TYPE")
    private String receiverInterchangeFeeType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SENDER_PROCESSING_FEE")
    private BigDecimal senderProcessingFee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "SENDER_PROCESSING_FEE_TYPE")
    private String senderProcessingFeeType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECEIVER_PROCESSING_FEE")
    private BigDecimal receiverProcessingFee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "RECEIVER_PROCESSING_FEE_TYPE")
    private String receiverProcessingFeeType;
    @Column(name = "MAX_SENDER_INTERCHANGE_FEE")
    private BigDecimal maxSenderInterchangeFee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_SENDER_INTERCHANGE_FEE_ENABLE")
    private int maxSenderInterchangeFeeEnable;
    @Column(name = "MAX_RECEIVER_INTERCHANGE_FEE")
    private BigDecimal maxReceiverInterchangeFee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_RECEIVER_INTERCHANGE_FEE_ENABLE")
    private int maxReceiverInterchangeFeeEnable;
    @Column(name = "MAX_SENDER_PROCESSING_FEE")
    private BigDecimal maxSenderProcessingFee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_SENDER_PROCESSING_FEE_ENABLE")
    private int maxSenderProcessingFeeEnable;
    @Column(name = "MAX_RECEIVER_PROCESSING_FEE")
    private BigDecimal maxReceiverProcessingFee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_RECEIVER_PROCESSING_FEE_ENABLE")
    private int maxReceiverProcessingFeeEnable;
    @Size(max = 500)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "DATE_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

    public TblTariffWithoutLadder() {
    }

    public TblTariffWithoutLadder(Integer tariffId) {
        this.tariffId = tariffId;
    }

    public TblTariffWithoutLadder(Integer tariffId, int tariffPlanId, String service, String boc, String ttc, String channel, String paymentCase, long valueRangeMin, long valueRangeMax, BigDecimal senderInterchangeFee, String senderInterchangeFeeType, BigDecimal receiverInterchangeFee, String receiverInterchangeFeeType, BigDecimal senderProcessingFee, String senderProcessingFeeType, BigDecimal receiverProcessingFee, String receiverProcessingFeeType, int maxSenderInterchangeFeeEnable, int maxReceiverInterchangeFeeEnable, int maxSenderProcessingFeeEnable, int maxReceiverProcessingFeeEnable) {
        this.tariffId = tariffId;
        this.tariffPlanId = tariffPlanId;
        this.service = service;
        this.boc = boc;
        this.ttc = ttc;
        this.channel = channel;
        this.paymentCase = paymentCase;
        this.valueRangeMin = valueRangeMin;
        this.valueRangeMax = valueRangeMax;
        this.senderInterchangeFee = senderInterchangeFee;
        this.senderInterchangeFeeType = senderInterchangeFeeType;
        this.receiverInterchangeFee = receiverInterchangeFee;
        this.receiverInterchangeFeeType = receiverInterchangeFeeType;
        this.senderProcessingFee = senderProcessingFee;
        this.senderProcessingFeeType = senderProcessingFeeType;
        this.receiverProcessingFee = receiverProcessingFee;
        this.receiverProcessingFeeType = receiverProcessingFeeType;
        this.maxSenderInterchangeFeeEnable = maxSenderInterchangeFeeEnable;
        this.maxReceiverInterchangeFeeEnable = maxReceiverInterchangeFeeEnable;
        this.maxSenderProcessingFeeEnable = maxSenderProcessingFeeEnable;
        this.maxReceiverProcessingFeeEnable = maxReceiverProcessingFeeEnable;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }

    public int getTariffPlanId() {
        return tariffPlanId;
    }

    public void setTariffPlanId(int tariffPlanId) {
        this.tariffPlanId = tariffPlanId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBoc() {
        return boc;
    }

    public void setBoc(String boc) {
        this.boc = boc;
    }

    public String getTtc() {
        return ttc;
    }

    public void setTtc(String ttc) {
        this.ttc = ttc;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPaymentCase() {
        return paymentCase;
    }

    public void setPaymentCase(String paymentCase) {
        this.paymentCase = paymentCase;
    }

    public long getValueRangeMin() {
        return valueRangeMin;
    }

    public void setValueRangeMin(long valueRangeMin) {
        this.valueRangeMin = valueRangeMin;
    }

    public long getValueRangeMax() {
        return valueRangeMax;
    }

    public void setValueRangeMax(long valueRangeMax) {
        this.valueRangeMax = valueRangeMax;
    }

    public BigDecimal getSenderInterchangeFee() {
        return senderInterchangeFee;
    }

    public void setSenderInterchangeFee(BigDecimal senderInterchangeFee) {
        this.senderInterchangeFee = senderInterchangeFee;
    }

    public String getSenderInterchangeFeeType() {
        return senderInterchangeFeeType;
    }

    public void setSenderInterchangeFeeType(String senderInterchangeFeeType) {
        this.senderInterchangeFeeType = senderInterchangeFeeType;
    }

    public BigDecimal getReceiverInterchangeFee() {
        return receiverInterchangeFee;
    }

    public void setReceiverInterchangeFee(BigDecimal receiverInterchangeFee) {
        this.receiverInterchangeFee = receiverInterchangeFee;
    }

    public String getReceiverInterchangeFeeType() {
        return receiverInterchangeFeeType;
    }

    public void setReceiverInterchangeFeeType(String receiverInterchangeFeeType) {
        this.receiverInterchangeFeeType = receiverInterchangeFeeType;
    }

    public BigDecimal getSenderProcessingFee() {
        return senderProcessingFee;
    }

    public void setSenderProcessingFee(BigDecimal senderProcessingFee) {
        this.senderProcessingFee = senderProcessingFee;
    }

    public String getSenderProcessingFeeType() {
        return senderProcessingFeeType;
    }

    public void setSenderProcessingFeeType(String senderProcessingFeeType) {
        this.senderProcessingFeeType = senderProcessingFeeType;
    }

    public BigDecimal getReceiverProcessingFee() {
        return receiverProcessingFee;
    }

    public void setReceiverProcessingFee(BigDecimal receiverProcessingFee) {
        this.receiverProcessingFee = receiverProcessingFee;
    }

    public String getReceiverProcessingFeeType() {
        return receiverProcessingFeeType;
    }

    public void setReceiverProcessingFeeType(String receiverProcessingFeeType) {
        this.receiverProcessingFeeType = receiverProcessingFeeType;
    }

    public BigDecimal getMaxSenderInterchangeFee() {
        return maxSenderInterchangeFee;
    }

    public void setMaxSenderInterchangeFee(BigDecimal maxSenderInterchangeFee) {
        this.maxSenderInterchangeFee = maxSenderInterchangeFee;
    }

    public int getMaxSenderInterchangeFeeEnable() {
        return maxSenderInterchangeFeeEnable;
    }

    public void setMaxSenderInterchangeFeeEnable(int maxSenderInterchangeFeeEnable) {
        this.maxSenderInterchangeFeeEnable = maxSenderInterchangeFeeEnable;
    }

    public BigDecimal getMaxReceiverInterchangeFee() {
        return maxReceiverInterchangeFee;
    }

    public void setMaxReceiverInterchangeFee(BigDecimal maxReceiverInterchangeFee) {
        this.maxReceiverInterchangeFee = maxReceiverInterchangeFee;
    }

    public int getMaxReceiverInterchangeFeeEnable() {
        return maxReceiverInterchangeFeeEnable;
    }

    public void setMaxReceiverInterchangeFeeEnable(int maxReceiverInterchangeFeeEnable) {
        this.maxReceiverInterchangeFeeEnable = maxReceiverInterchangeFeeEnable;
    }

    public BigDecimal getMaxSenderProcessingFee() {
        return maxSenderProcessingFee;
    }

    public void setMaxSenderProcessingFee(BigDecimal maxSenderProcessingFee) {
        this.maxSenderProcessingFee = maxSenderProcessingFee;
    }

    public int getMaxSenderProcessingFeeEnable() {
        return maxSenderProcessingFeeEnable;
    }

    public void setMaxSenderProcessingFeeEnable(int maxSenderProcessingFeeEnable) {
        this.maxSenderProcessingFeeEnable = maxSenderProcessingFeeEnable;
    }

    public BigDecimal getMaxReceiverProcessingFee() {
        return maxReceiverProcessingFee;
    }

    public void setMaxReceiverProcessingFee(BigDecimal maxReceiverProcessingFee) {
        this.maxReceiverProcessingFee = maxReceiverProcessingFee;
    }

    public int getMaxReceiverProcessingFeeEnable() {
        return maxReceiverProcessingFeeEnable;
    }

    public void setMaxReceiverProcessingFeeEnable(int maxReceiverProcessingFeeEnable) {
        this.maxReceiverProcessingFeeEnable = maxReceiverProcessingFeeEnable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tariffId != null ? tariffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTariffWithoutLadder)) {
            return false;
        }
        TblTariffWithoutLadder other = (TblTariffWithoutLadder) object;
        if ((this.tariffId == null && other.tariffId != null) || (this.tariffId != null && !this.tariffId.equals(other.tariffId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.entity.TblTariffWithoutLadder[ tariffId=" + tariffId + " ]";
    }

}
