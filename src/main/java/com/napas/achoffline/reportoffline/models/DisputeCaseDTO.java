package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;


@Data
public class DisputeCaseDTO {
    private Long dispId;
    private String reference;
    private Long assigner;
    private Long assignee;
    private Long dispCatId;
    private String description;
    private Long clmInstrRef;
    private Long amount;
    private Long settledTrnRef;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date modifDate;
    private Long modifUserId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Timestamp mDate;
    private Long statusId;
    private Long mChangeId;
    private Long respondent;
    private Long currencyRef;
    private Long declineCount;
    private Long clmDocId;
    private Long settledDocId;
    private Long stageId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date stageStartDate;
    private String participantAssigner;
    private String assignerName;
    private String participantAssignee;
    private String assigneeName;
    private String participantRespondent;
    private String respondentName;
    private String statusCode;
    private String code;
    private String txid;
    private String requestNum;
    private String categoriesName;
    private Long responseTimeNum;
    private Long operdayId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date operday;
    private String statusDescription;
    private String instrid;
    private String endtoendid;
    private String bankOpCode;
    private String transTypeCode;
    private Long pdAmount;
    private Long pdiAmount;
    private String responseTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date responseDate;
    private String debtorName;
    private String debtorAccount;
    private String creditorName;
    private String creditorAccount;
}
