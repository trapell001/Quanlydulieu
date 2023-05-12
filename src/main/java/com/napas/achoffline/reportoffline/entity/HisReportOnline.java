package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HIS_REPORT_ONLINE")
public class HisReportOnline implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "ID")
    private Long Id;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    private Date creatDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "RPT_TYPE")
    private String rptType;

    @Column(name = "PARTICIPANT")
    private String participant;


    @Column(name = "ACCOUNT")
    private String account;


    @Column(name = "SYSTEM_MODULE")
    private String systemModule;


    @Column(name = "PAGE_NUMBER")
    private Long pageNumber;


    @Column(name = "STATUS")
    private String status;

    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Column(name = "TOTAL_PAGE")
    private Long totalPage;

    @Column(name = "NBOFNTRIES")
    private Long nbofntries;

    @Column(name = "DELIVERY_METHOD")
    private String deliveryMethod;

    @Column(name = "RAW_MESSAGE")
    private String rawMessage;

    @Column(name = "MSGID")
    private String msgId;

    @Column(name = "GROUP_MSGID")
    private String groupMsgId;

    @Column(name = "TOTAL_ENTRIES")
    private Long totalEntries;

    @Column(name = "FIRE_TYPE")
    private String fireType;

    @Column(name = "HMAC_DIGEST")
    private String hmacDigest;

}
