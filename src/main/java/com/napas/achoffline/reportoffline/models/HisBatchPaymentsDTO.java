package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class HisBatchPaymentsDTO {
    private Long docId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date processDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date modifDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date firstSyncDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date lastSyncDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Timestamp mxTransDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mxValueDate;
    private Long sessionId;
    private Long operdayId;
    private Long transId;
    private Long stepId;
    private Long packId;
    private String mxMsgid;
    private String mxOriginMsgid;
    private String mxType;
    private String mxBankOpCode;
    private String mxTransTypeCode;
    private Long mxPriority;
    private String transStatus;
    private Long resultId;
    private Long initialNumOfInstr;
    private Long mxNumOfInstr;
    private Long initialAmount;
    private BigDecimal mxTtlSettlementAmount;
    private String mxSettlementCurrency;
    private String mxInstructingAgent;
    private String channelId;
    private String mxDebtorAgent;
    private String mxInstructedAgent;
    private String mxCreditorAgent;
    private String currency;
    private String viewStatus;
    private String errorCode;
    private String mxMessageSender;
}
