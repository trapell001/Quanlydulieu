package com.napas.achoffline.reportoffline.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class BatchInstrStatistic {
    private String date;
    private String ttc;
    private String debtorAgent;
    private String msgId;
    private String transStatus;
    private Long soLuong;
    private BigDecimal amount;

    private Date convertDate;

    private String errorCode;

    public BatchInstrStatistic(String ttc, String debtorAgent, String msgId, String transStatus,
                               String errorCode, Long soLuong, BigDecimal amount) {
        this.ttc = ttc;
        this.debtorAgent = debtorAgent;
        this.msgId = msgId;
        this.transStatus = transStatus;
        this.errorCode = errorCode;
        this.soLuong = soLuong;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BatchInstrStatistic{" +
                "date='" + date + '\'' +
                ", ttc='" + ttc + '\'' +
                ", debtorAgent='" + debtorAgent + '\'' +
                ", msgId='" + msgId + '\'' +
                ", transStatus='" + transStatus + '\'' +
                ", soLuong=" + soLuong +
                ", amount=" + amount +
                '}';
    }
}
