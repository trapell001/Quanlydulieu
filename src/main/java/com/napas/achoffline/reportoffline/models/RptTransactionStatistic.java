package com.napas.achoffline.reportoffline.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RptTransactionStatistic {
    private String date;
    private String rtpType;
    private String ttc;
    private String tcpl;
    private String tcnl;
    private String tcth;
    private String status;
    private Long soLuong;

    private Date convertDate;

    private String responseCode;

    private String creditorSystem;

    public RptTransactionStatistic(String rtpType, String ttc, String tcpl, String tcnl, String tcth,
                                   String status, Long soLuong, String responseCode) {
        this.rtpType = rtpType;
        this.ttc = ttc;
        this.tcpl = tcpl;
        this.tcnl = tcnl;
        this.tcth = tcth;
        this.status = status;
        this.soLuong = soLuong;
        this.responseCode = responseCode;
    }
}
