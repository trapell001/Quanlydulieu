/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.napas.achoffline.reportoffline.define.TariffType;
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
public class HisNrtPaymentsFeeDTO  {
    private Long docId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date processDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date creationDateTime;
    private long sessionId;
    private String txid;
    private String instrId;
    private String endToEndId;
    private BigDecimal amount;
    private String debtorAgent;
    private String creditorAgent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private Date valueDate;
    private String msgType;
    private String ttc;
    private String channelId;
    private Integer tariffId;
    private Short addedPaymentFlag;
    private Short specialBankAccountFlag;
    private BigDecimal feeIrfIss;
    private BigDecimal feeSvfIss;
    private BigDecimal feeIrfRcv;
    private BigDecimal feeSvfRcv;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date calculatedDate;
    private String originalTxid;
    private Long monthOrder;
    private Integer tariffIdForBen;

    private TariffType tariffType;
    private Integer tariffWithoutLadderId;
    private Integer specialChannelFlag;
    private Integer specialBankDestFlag;
    private String originalTxidForReturn;
    private String boc;


}
