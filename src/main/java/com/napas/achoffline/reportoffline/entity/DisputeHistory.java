package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DISPUTE_HISTORY")
public class DisputeHistory {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DISP_HIST_ID")
    private Long dispHistId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "DISP_ID")
    private Long dispId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "MSG_TEXT")
    private String msgText;


    @Basic(optional = false)
    @NotNull
    @Column(name = "QUERY_ID")
    private Long queryId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "ATCH_NUM")
    private Long atchNum;


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
    @Column(name = "M_CHANGE_ID")
    private Long mChangeId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS_ID")
    private Long statusId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_READ")
    private String isRead;


    @Basic(optional = false)
    @NotNull
    @Column(name = "PREVIOUS_STATUS_ID")
    private Long previousStatusId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "REPORTER")
    private Long reporter;


    @Basic(optional = false)
    @NotNull
    @Column(name = "OPERDAY")
    private Date operday;


    @Basic(optional = false)
    @NotNull
    @Column(name = "STAGE_ID")
    private Long stageId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "PREV_STAGE_ID")
    private Long prevStageId;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STAGE_START_DATE")
    private Date stageStartDate;


    @Basic(optional = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PREV_STAGE_START_DATE")
    private Date prevStageStartDate;


    @Basic(optional = false)
    @NotNull
    @Column(name = "DECLINE_REASON_ID")
    private Long declineReasonId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "REASON_ID")
    private Long reasonId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "PREV_REASON_ID")
    private Long prevReasonId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS_CODE")
    private String statusCode;


    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS_DESCRIPTION")
    private String statusDescription;


    @Basic(optional = false)
    @NotNull
    @Column(name = "PREVIOUS_STATUS_CODE")
    private String previousStatusCode;


    @Basic(optional = false)
    @NotNull
    @Column(name = "PREVIOUS_STATUS_DESCRIPTION")
    private String previousStatusDescription;
}
