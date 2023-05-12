package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.geometry.euclidean.oned.Interval;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DISPUTE_CASE")
public class DisputeCase implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DISP_ID")
    private Long dispId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "REFERENCE")
    private String reference;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ASSIGNER")
    private Long assigner;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ASSIGNEE")
    private Long assignee;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DISP_CAT_ID")
    private Long dispCatId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CLM_INSTR_REF")
    private Long clmInstrRef;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private Long amount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SETTLED_TRN_REF")
    private Long settledTrnRef;

    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIF_DATE")
    private Date modifDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MODIF_USER_ID")
    private Long modifUserId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "M_DATE")
    private Timestamp mDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS_ID")
    private Long statusId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CHANGE_ID")
    private Long mChangeId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RESPONDENT")
    private Long respondent;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CURRENCY_REF")
    private Long currencyRef;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DECLINE_COUNT")
    private Long declineCount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CLM_DOC_ID")
    private Long clmDocId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "SETTLED_DOC_ID")
    private Long settledDocId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "STAGE_ID")
    private Long stageId;

    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STAGE_START_DATE")
    private Date stageStartDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "REASON_ID")
    private Long reasonId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PARTICIPANT_ASSIGNER")
    private String participantAssigner;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ASSIGNER_NAME")
    private String assignerName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PARTICIPANT_ASSIGNEE")
    private String participantAssignee;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ASSIGNEE_NAME")
    private String assigneeName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PARTICIPANT_RESPONDENT")
    private String participantRespondent;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RESPONDENT_NAME")
    private String respondentName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS_CODE")
    private String statusCode;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE")
    private String code;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TXID")
    private String txid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "REQUEST_NUM")
    private String requestNum;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CATEGORIES_NAME")
    private String categoriesName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RESPONSE_TIME_NUM")
    private Long responseTimeNum;

    @Basic(optional = false)
    @NotNull
    @Column(name = "OPERDAY_ID")
    private Long operdayId;

    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "OPERDAY")
    private Date operday;

    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS_DESCRIPTION")
    private String statusDescription;

    @Basic(optional = false)
    @NotNull
    @Column(name = "INSTRID")
    private String instrid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ENDTOENDID")
    private String endtoendid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "BANK_OP_CODE")
    private String bankOpCode;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANS_TYPE_CODE")
    private String transTypeCode;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PD_AMOUNT")
    private Long pdAmount;


    @Basic(optional = false)
    @NotNull
    @Column(name = "PDI_AMOUNT")
    private Long pdiAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RESPONSE_TIME")
    private String responseTime;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DEBTOR_NAME")
    private String debtorName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DEBTOR_ACCOUNT")
    private String debtorAccount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDITOR_NAME")
    private String creditorName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDITOR_ACCOUNT")
    private String creditorAccount;

    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "RESPONSE_DATE")
    private Date responseDate;
}
