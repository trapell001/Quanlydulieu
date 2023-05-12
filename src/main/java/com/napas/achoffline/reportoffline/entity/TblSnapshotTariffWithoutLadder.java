package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "TBL_SNAPSHOT_TARIFF_WITHOUT_LADDER")
@NamedQueries({
        @NamedQuery(name = "TblSnapshotTariffWithoutLadder.findAll", query = "SELECT c FROM TblSnapshotTariffWithoutLadder c")})
public class TblSnapshotTariffWithoutLadder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_ID")
    private Long tariffId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_PLAN_ID")
    private Long tariffPlanId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SERVICE")
    private String service;

    @Basic(optional = false)
    @NotNull
    @Column(name = "BOC")
    private String boc;


    @Basic(optional = false)
    @NotNull
    @Column(name = "TTC")
    private String ttc;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CHANNEL")
    private String channel;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYMENT_CASE")
    private String paymentCase;

    @Basic(optional = false)
    @NotNull
    @Column(name = "VALUE_RANGE_MIN")
    private Long valueRangeMin;

    @Basic(optional = false)
    @NotNull
    @Column(name = "VALUE_RANGE_MAX")
    private Long valueRangeMax;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SENDER_INTERCHANGE_FEE")
    private Long senderInterchangeFee;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SENDER_INTERCHANGE_FEE_TYPE")
    private String senderInterchangeFeeType;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RECEIVER_INTERCHANGE_FEE")
    private Long receiverInterchangeFee;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RECEIVER_INTERCHANGE_FEE_TYPE")
    private String receiverInterchangeFeeType;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SENDER_PROCESSING_FEE")
    private Long senderProcessingFee;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SENDER_PROCESSING_FEE_TYPE")
    private String senderProcessingFeeType;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RECEIVER_PROCESSING_FEE")
    private Long receiverProcessingFee;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RECEIVER_PROCESSING_FEE_TYPE")
    private String receiverProcessingFeeType;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_SENDER_INTERCHANGE_FEE")
    private Long maxSenderInterchangeFee;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_SENDER_INTERCHANGE_FEE_ENABLE")
    private Long maxSenderInterchangeFeeEnable;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_RECEIVER_INTERCHANGE_FEE")
    private Long maxReceiverInterchangeFee;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_RECEIVER_INTERCHANGE_FEE_ENABLE")
    private Long maxReceiverInterchangeFeeEnable;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_SENDER_PROCESSING_FEE")
    private Long maxSenderProcessingFee;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_SENDER_PROCESSING_FEE_ENABLE")
    private Long maxSenderProcessingFeeEnable;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_RECEIVER_PROCESSING_FEE")
    private Long maxReceiverProcessingFee;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_RECEIVER_PROCESSING_FEE_ENABLE")
    private Long maxReceiverProcessingFeeEnable;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_SNAPSHOTTED")
    private Date dateSnapshotted;
}
