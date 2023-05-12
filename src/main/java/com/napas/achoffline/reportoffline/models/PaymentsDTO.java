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
import java.time.OffsetDateTime;
import java.util.Date;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentsDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date processDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date respondDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date modifDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date aFirstSyncDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date aLastSyncDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private OffsetDateTime transDate;

    private String iTDT;

    private Long sessionId;

    private Long transId;

    private Long docId;

    private Long aStepId;

    private Long aPackId;

    private String msgID;

    private String origMsgID;

    private String txid;

    private String type;

    private String mxBankOpCode;

    private String paymentType;

    private String channelId;

    private String serviceCode;

    private String transTypeCode;

    private String merchantType;

    private String posEntryMode;

    private String posCondition;

    private Long priority;

    private String transStatus;

    private String authInfo;

    private BigDecimal ttlSettlementAmount;

    private BigDecimal settlementAmount;

    private String settlementCurrency;

    private String transactionAmount;

    private String transactionCurrency;

    private String iAIC;

    private String iSCR;

    private String instructingAgent;

    private String debtorAgent;

    private String iFID;

    private String debtorName;

    private String debtorAccount;

    private String iFAI;

    private String mxDebtorAccountType;

    private String debtorAdrline;
    private String instructedAgent;

    private String creditorAgent;

    private String iBID;

    private String creditorName;

    private String creditorAccount;

    private String iTAI;

    private String mxCreditorAccountType;

    private String creditorAdrline;

    private String msgToCreditor;

    private String merchantName;

    private String systemTrace;

    private String termID;

    private String merchantID;

    private String txrnRef;

    private String transRefNum;

    private String instrid;

    private String endtoendid;

    private String mxTTC;

    private Integer aOperdayId;

    private String air;

    private String achResultCode;

    private String achResultCodeDesc;

    private String partyResultCode;

    private String partyResultCodeDesc;

    private String debtorBankId;

    private Integer haveReturn;

    private String settYear;

    private String rtpPmtinfid;

    private String rtpIssuer;

    private String businessSvcCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Asia/Ho_Chi_Minh")
    private Date mxValueDate;

    private String settDate;

    private String rc;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Asia/Ho_Chi_Minh")
    private Date mxOrigValueDate;

    private String mxOrigTxid;

    private String mxPartyResultDetail;

    private String mxReturnReason;

    private String mxReturnReasonDetail;

    private String systemToSystem;

    private Long lateRespond;

}
