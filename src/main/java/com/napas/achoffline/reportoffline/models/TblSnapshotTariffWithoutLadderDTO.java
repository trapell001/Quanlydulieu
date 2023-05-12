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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblSnapshotTariffWithoutLadderDTO {
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Column(name = "TARIFF_ID")
    private Long tariffId;

    @Column(name = "TARIFF_PLAN_ID")
    private Long tariffPlanId;

    @Column(name = "SERVICE")
    private String service;

    @Column(name = "BOC")
    private String boc;

    @Column(name = "TTC")
    private String ttc;

    @Column(name = "CHANNEL")
    private String channel;

    @Column(name = "PAYMENT_CASE")
    private String paymentCase;

    @Column(name = "VALUE_RANGE_MIN")
    private Long valueRangeMin;

    @Column(name = "VALUE_RANGE_MAX")
    private Long valueRangeMax;

    @Column(name = "SENDER_INTERCHANGE_FEE")
    private Long senderInterchangeFee;

    @Column(name = "SENDER_INTERCHANGE_FEE_TYPE")
    private String senderInterchangeFeeType;

    @Column(name = "RECEIVER_INTERCHANGE_FEE")
    private Long receiverInterchangeFee;

    @Column(name = "RECEIVER_INTERCHANGE_FEE_TYPE")
    private String receiverInterchangeFeeType;

    @Column(name = "SENDER_PROCESSING_FEE")
    private Long senderProcessingFee;

    @Column(name = "SENDER_PROCESSING_FEE_TYPE")
    private String senderProcessingFeeType;

    @Column(name = "RECEIVER_PROCESSING_FEE")
    private Long receiverProcessingFee;

    @Column(name = "RECEIVER_PROCESSING_FEE_TYPE")
    private String receiverProcessingFeeType;

    @Column(name = "MAX_SENDER_INTERCHANGE_FEE")
    private Long maxSenderInterchangeFee;

    @Column(name = "MAX_SENDER_INTERCHANGE_FEE_ENABLE")
    private Long maxSenderInterchangeFeeEnable;

    @Column(name = "MAX_RECEIVER_INTERCHANGE_FEE")
    private Long maxReceiverInterchangeFee;

    @Column(name = "MAX_RECEIVER_INTERCHANGE_FEE_ENABLE")
    private Long maxReceiverInterchangeFeeEnable;

    @Column(name = "MAX_SENDER_PROCESSING_FEE")
    private Long maxSenderProcessingFee;

    @Column(name = "MAX_SENDER_PROCESSING_FEE_ENABLE")
    private Long maxSenderProcessingFeeEnable;

    @Column(name = "MAX_RECEIVER_PROCESSING_FEE")
    private Long maxReceiverProcessingFee;

    @Column(name = "MAX_RECEIVER_PROCESSING_FEE_ENABLE")
    private Long maxReceiverProcessingFeeEnable;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE_SNAPSHOTTED")
    private Date dateSnapshotted;
}
