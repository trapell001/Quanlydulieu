package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "HIS_BATCH_INSTR")
public class HisBatchInstr implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PD_INSTR_ID")
    private Long pdInstrId;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    @Column(name = "A_PROCESS_DATE")
    private Date processDate;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
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
    @Column(name = "I_TDT")
    private String iTdt;


    @Basic(optional = false)
    @NotNull
    @Column(name = "A_DOC_ID")
    private Long docId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_TXID")
    private String mxTxid;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_ORIGIN_TXID")
    private String mxOriginTxid;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_TTC")
    private String mxTtc;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_PAYMENT_TYPE")
    private String iPaymentType;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_CHANNEL_ID")
    private String iChannelId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_SERVICE_CODE")
    private String iServiceCode;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_MERCHANT_TYPE")
    private String iMerchantType;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_PEM")
    private String iPem;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_PCD")
    private String iPcd;


    @Basic(optional = false)
    @NotNull
    @Column(name = "A_STATUS")
    private String status;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_SETTLEMENT_AMOUNT")
    private BigDecimal mxSettlementAmount;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_TRANSACTION_AMOUNT")
    private String iTransactionAmount;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_TRANSACTION_CURRENCY")
    private String iTransactionCurrency;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_AIC")
    private String iAic;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_SCR")
    private String iScr;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_INSTRUCTING_AGENT")
    private String mxInstructingAgent;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_DEBTOR_AGENT")
    private String mxDebtorAgent;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_FID")
    private String iFid;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_DEBTOR_NAME")
    private String mxDebtorName;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_DEBTOR_ACCOUNT")
    private String mxDebtorAccount;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_FAI")
    private String iFai;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_DEBTOR_ACCOUNT_TYPE")
    private String mxDebtorAccountType;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_DEBTOR_ADRLINE")
    private String mxDebtorAdrline;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_INSTRUCTED_AGENT")
    private String mxInstructedAgent;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_CREDITOR_AGENT")
    private String mxCreditorAgent;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_BID")
    private String iBid;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_CREDITOR_NAME")
    private String mxCreditorName;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_CREDITOR_ACCOUNT")
    private String mxCreditorAccount;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_TAI")
    private String iTai;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_CREDITOR_ACCOUNT_TYPE")
    private String mxCreditorAccountType;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_CREDITOR_ADRLINE")
    private String mxCreditorAdrline;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_MSG_TO_CREDITOR")
    private String iMsgToCreditor;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_MERCHANT_NAME")
    private String iMerchantName;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_SYSTEM_TRACE")
    private String iSystemTrace;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_TERM_ID")
    private String iTermId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_MERCHANT_ID")
    private String iMerchantId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_TRXN_REF")
    private String iTrxnRef;


    @Basic(optional = false)
    @NotNull
    @Column(name = "I_TRANS_REF_NUM")
    private String iTransRefNum;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_INSTRID")
    private String mxInstrid;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_ENDTOENDID")
    private String mxEndtoendid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "A_VIEW_STATUS")
    private String viewStatus;

    @Basic(optional = false)
    @NotNull
    @Column(name = "A_RESULT_CODE")
    private String resultCode;

    @Column(name = "A_RTP_PMTINFID")
    private String rtpPmtinfid;

    @Column(name = "A_RTP_ISSUER")
    private String rtpIssuer;
}
