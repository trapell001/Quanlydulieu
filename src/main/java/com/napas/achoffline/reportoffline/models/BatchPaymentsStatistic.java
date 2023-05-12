package com.napas.achoffline.reportoffline.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
public class BatchPaymentsStatistic {
    private String ttc;
    private String instructing;
    private String transStatus;
    private Long soLuong;

    private String date;

    private Date convertDate;

    private String errorCode;

    public BatchPaymentsStatistic() {
    }

    public BatchPaymentsStatistic(String ttc, String instructing, String transStatus, String errorCode, Long soLuong) {
        this.ttc = ttc;
        this.instructing = instructing;
        this.transStatus = transStatus;
        this.errorCode = errorCode;
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "BatchPaymentsStatistic{" +
                "ttc='" + ttc + '\'' +
                ", instructing='" + instructing + '\'' +
                ", transStatus='" + transStatus + '\'' +
                ", soLuong=" + soLuong +
                ", date='" + date + '\'' +
                '}';
    }
}
