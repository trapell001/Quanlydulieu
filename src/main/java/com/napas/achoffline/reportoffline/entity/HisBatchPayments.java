package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "HIS_BATCH_PAYMENTS")
public class HisBatchPayments implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "A_DOC_ID")
    private Long docId;

    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    @Column(name = "A_PROCESS_DATE")
    private Date processDate;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    @Column(name = "A_MODIF_DATE")
    private Date modifDate;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    @Column(name = "A_FIRST_SYNC_DATE")
    private Date firstSyncDate;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    @Column(name = "A_LAST_SYNC_DATE")
    private Date lastSyncDate;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    @Column(name = "MX_TRANS_DATE")
    private Timestamp mxTransDate;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    @Column(name = "MX_VALUE_DATE")
    private Date mxValueDate;


    @Basic(optional = false)
    @NotNull
    @Column(name = "A_SESSION_ID")
    private Long sessionId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "A_OPERDAY_ID")
    private Long operdayId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "A_TRANS_ID")
    private Long transId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "A_STEP_ID")
    private Long stepId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "A_PACK_ID")
    private Long packId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_MSGID")
    private String mxMsgid;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_ORIGIN_MSGID")
    private String mxOriginMsgid;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_TYPE")
    private String mxType;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_BANK_OP_CODE")
    private String mxBankOpCode;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_TRANS_TYPE_CODE")
    private String mxTransTypeCode;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_PRIORITY")
    private Long mxPriority;


    @Basic(optional = false)
    @NotNull
    @Column(name = "A_TRANS_STATUS")
    private String transStatus;


    @Basic(optional = false)
    @NotNull
    @Column(name = "A_RESULT_ID")
    private Long tesultId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "A_INITIAL_NUM_OF_INSTR")
    private Long initialNumOfInstr;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_NUM_OF_INSTR")
    private Long mxNumOfInstr;


    @Basic(optional = false)
    @NotNull
    @Column(name = "A_INITIAL_AMOUNT")
    private Long initialAmount;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_TTL_SETTLEMENT_AMOUNT")
    private BigDecimal mxTtlSettlementAmount;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_SETTLEMENT_CURRENCY")
    private String mxSettlementCurrency;

    @Basic(optional = false)
    @NotNull
    @Column(name = "A_VIEW_STATUS")
    private String viewStatus;

    @Basic(optional = false)
    @NotNull
    @Column(name = "A_ERROR_CODE")
    private String errorCode;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_MESSAGE_SENDER")
    private String mxMessageSender;
}
