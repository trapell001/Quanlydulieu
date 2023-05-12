package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class HisBatchInstrDTO {
    private Long pdInstrId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date processDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date modifDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date firstSyncDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date lastSyncDate;
    private String iTdt;
    private Long docId;
    private String mxTxid;
    private String mxOriginTxid;
    private String mxTtc;
    private String iPaymentType;
    private String iChannelId;
    private String iServiceCode;
    private String iMerchantType;
    private String iPem;
    private String iPcd;
    private String status;
    private BigDecimal mxSettlementAmount;
    private String iTransactionAmount;
    private String iTransactionCurrency;
    private String iAic;
    private String iScr;
    private String mxInstructingAgent;
    private String mxDebtorAgent;
    private String iFid;
    private String mxDebtorName;
    private String mxDebtorAccount;
    private String iFai;
    private String mxDebtorAccountType;
    private String mxDebtorAdrline;
    private String mxInstructedAgent;
    private String mxCreditorAgent;
    private String iBid;
    private String mxCreditorName;
    private String mxCreditorAccount;
    private String iTai;
    private String mxCreditorAccountType;
    private String mxCreditorAdrline;
    private String iMsgToCreditor;
    private String iMerchantName;
    private String iSystemTrace;
    private String iTermId;
    private String iMerchantId;
    private String iTrxnRef;
    private String iTransRefNum;
    private String mxInstrid;
    private String mxEndtoendid;
    private String boc;
    private Long session;
    private Long operdayId;
    private Long priority;
    private String msgId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date valueDate;
    private String viewStatus;
    private String resultCode;
    private String rtpPmtinfid;
    private String rtpIssuer;
}
