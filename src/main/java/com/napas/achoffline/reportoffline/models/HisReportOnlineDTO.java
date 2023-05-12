package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HisReportOnlineDTO {
    private Long Id;

    private String creatDate;


    private String startDate;


    private String endDate;

    private String rptType;

    private String participant;


    private String account;


    private String systemModule;


    private Long pageNumber;


    private String status;

    private Long sessionId;

    private Long totalPage;

    private Long nbofntries;

    private String deliveryMethod;

    private String msgId;

    private String groupMsgId;

    private Long totalEntries;

    private String fireType;

    private String hmacDigest;

}
