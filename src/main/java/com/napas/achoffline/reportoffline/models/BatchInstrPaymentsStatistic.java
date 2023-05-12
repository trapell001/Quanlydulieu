package com.napas.achoffline.reportoffline.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class BatchInstrPaymentsStatistic {
    private String date;
    private String debtorAgent;
    private String creditorAgent;
    private String ttc;
    private String transStatus;
    private Long soLuong;
    private BigDecimal amount;

    private Date convertDate;

    private String errorCode;

    public BatchInstrPaymentsStatistic(String debtorAgent, String creditorAgent, String ttc, String transStatus,
                                       String errorCode, Long soLuong, BigDecimal amount) {
        this.debtorAgent = debtorAgent;
        this.creditorAgent = creditorAgent;
        this.ttc = ttc;
        this.transStatus = transStatus;
        this.errorCode = errorCode;
        this.soLuong = soLuong;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BatchInstrPaymentsStatistic{" +
                "date='" + date + '\'' +
                ", debtorAgent='" + debtorAgent + '\'' +
                ", creditorAgent='" + creditorAgent + '\'' +
                ", ttc='" + ttc + '\'' +
                ", transStatus='" + transStatus + '\'' +
                ", soLuong=" + soLuong +
                ", amount=" + amount +
                '}';
    }
}
