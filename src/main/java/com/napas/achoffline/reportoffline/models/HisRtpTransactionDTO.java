package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class HisRtpTransactionDTO {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date mxTransDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date processDate;
    private String rtpServiceType;
    private String initiatingParty;
    private String forwardingAgent;
    private String batchMsgId;
    private String paymentInfoId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date requestedExecutionDate;
    private String debtorName;
    private String debtorAddr;
    private String debtorAccount;
    private String debtorAccountType;
    private String debtorAgent;
    private String creditorName;
    private String creditorAddr;
    private String creditorAccount;
    private String creditorAccountType;
    private Long instructedAmount;
    private String regulatoryReporting;
    private String remittanceInformation;
    private String instructionId;
    private String endToEndId;
    private String serviceLevel;
    private String bankOperationCode;
    private String chargeBearer;
    private String instructedCurrency;
    private String creditorAgent;
    private Long sessionId;
    private String transStatus;
    private String respondCode;
    private String respondDescription;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date receivedRespondDate;
    private String mxTxid;
    private String ttc;
    private String creditorSystem;
}
