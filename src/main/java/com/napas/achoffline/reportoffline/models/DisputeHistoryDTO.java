package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class DisputeHistoryDTO {
    //    private Long dispHistId;
    private Long dispId;
    private String msgText;
    //    private Long queryId;
    private Long atchNum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date modifDate;
    //    private Long modifUserId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Timestamp mDate;
    //    private Long mChangeId;
    private Long statusId;
    //    private String isRead;
//    private Long previousStatusId;
//    private Long reporter;
//    private Date operday;
//    private Long stageId;
//    private Long prevStageId;
//    private Date stageStartDate;
//    private Date prevStageStartDate;
//    private Long declineReasonId;
//    private Long reasonId;
//    private Long prevReasonId;
    private String statusCode;
    private String statusDescription;
    private String previousStatusCode;
    private String previousStatusDescription;
    private String responseTime;
}
