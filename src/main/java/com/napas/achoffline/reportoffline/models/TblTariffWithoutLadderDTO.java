/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblTariffWithoutLadderDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer tariffId;
    private int tariffPlanId;
    private String service;
    private String boc;
    private String ttc;
    private String channel;
    private String paymentCase;
    private long valueRangeMin;
    private long valueRangeMax;
    private BigDecimal senderInterchangeFee;
    private String senderInterchangeFeeType;
    private BigDecimal receiverInterchangeFee;
    private String receiverInterchangeFeeType;
    private BigDecimal senderProcessingFee;
    private String senderProcessingFeeType;
    private BigDecimal receiverProcessingFee;
    private String receiverProcessingFeeType;
    private BigDecimal maxSenderInterchangeFee;
    private int maxSenderInterchangeFeeEnable;
    private BigDecimal maxReceiverInterchangeFee;
    private int maxReceiverInterchangeFeeEnable;
    private BigDecimal maxSenderProcessingFee;
    private int maxSenderProcessingFeeEnable;
    private BigDecimal maxReceiverProcessingFee;
    private int maxReceiverProcessingFeeEnable;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date dateModified;
}
