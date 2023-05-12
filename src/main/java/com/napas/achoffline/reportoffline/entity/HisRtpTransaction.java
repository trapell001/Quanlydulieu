package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "HIS_RTP_TRANSACTION")
public class HisRtpTransaction {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "MX_TRANS_DATE")
    private Date mxTransDate;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "A_PROCESS_DATE")
    private Date processDate;


    @Basic(optional = false)
    @NotNull
    @Column(name = "RTP_SERVICE_TYPE")
    private String rtpServiceType;


    @Basic(optional = false)
    @NotNull
    @Column(name = "INITIATING_PARTY")
    private String initiatingParty;


    @Basic(optional = false)
    @NotNull
    @Column(name = "FORWARDING_AGENT")
    private String forwardingAgent;


    @Basic(optional = false)
    @NotNull
    @Column(name = "BATCH_MSG_ID")
    private String batchMsgId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYMENT_INFO_ID")
    private String paymentInfoId;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "REQUESTED_EXECUTION_DATE")
    private Date requestedExecutionDate;


    @Basic(optional = false)
    @NotNull
    @Column(name = "DEBTOR_NAME")
    private String debtorName;


    @Basic(optional = false)
    @NotNull
    @Column(name = "DEBTOR_ADDR")
    private String debtorAddr;


    @Basic(optional = false)
    @NotNull
    @Column(name = "DEBTOR_ACCOUNT")
    private String debtorAccount;


    @Basic(optional = false)
    @NotNull
    @Column(name = "DEBTOR_ACCOUNT_TYPE")
    private String debtorAccountType;


    @Basic(optional = false)
    @NotNull
    @Column(name = "DEBTOR_AGENT")
    private String debtorAgent;


    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDITOR_NAME")
    private String creditorName;


    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDITOR_ADDR")
    private String creditorAddr;


    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDITOR_ACCOUNT")
    private String creditorAccount;


    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDITOR_ACCOUNT_TYPE")
    private String creditorAccountType;


    @Basic(optional = false)
    @NotNull
    @Column(name = "INSTRUCTED_AMOUNT")
    private Long instructedAmount;


    @Basic(optional = false)
    @NotNull
    @Column(name = "REGULATORY_REPORTING")
    private String regulatoryReporting;


    @Basic(optional = false)
    @NotNull
    @Column(name = "REMITTANCE_INFORMATION")
    private String remittanceInformation;


    @Basic(optional = false)
    @NotNull
    @Column(name = "INSTRUCTION_ID")
    private String instructionId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "END_TO_END_ID")
    private String endToEndId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "SERVICE_LEVEL")
    private String serviceLevel;


    @Basic(optional = false)
    @NotNull
    @Column(name = "BANK_OPERATION_CODE")
    private String bankOperationCode;


    @Basic(optional = false)
    @NotNull
    @Column(name = "CHARGE_BEARER")
    private String chargeBearer;


    @Basic(optional = false)
    @NotNull
    @Column(name = "INSTRUCTED_CURRENCY")
    private String instructedCurrency;


    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDITOR_AGENT")
    private String creditorAgent;

    @Basic(optional = false)
    @NotNull
    @Column(name = "A_SESSION_ID")
    private Long sessionId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANS_STATUS")
    private String transStatus;


    @Basic(optional = false)
    @NotNull
    @Column(name = "RESPOND_CODE")
    private String respondCode;


    @Basic(optional = false)
    @NotNull
    @Column(name = "RESPOND_DESCRIPTION")
    private String respondDescription;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "RECEIVED_RESPOND_DATE")
    private Date receivedRespondDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MX_TXID")
    private String mxTxid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TTC")
    private String ttc;
}
