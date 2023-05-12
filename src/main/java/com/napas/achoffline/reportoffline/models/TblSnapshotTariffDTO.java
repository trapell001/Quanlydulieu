package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TblSnapshotTariffDTO {

    @Basic(optional = false)
    @NotNull
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_ID")
    private Long tariffId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_LADDER_ID")
    private Long tariffLadderId;

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
