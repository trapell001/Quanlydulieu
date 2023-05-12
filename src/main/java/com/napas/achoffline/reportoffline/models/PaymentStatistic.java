package com.napas.achoffline.reportoffline.models;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentStatistic {
    private String instructingAgent;
    private String instructedAgent;
    private String ttc;
    private String transType;
    private String channel;
    private String creditorType;
    private String status;
    private String auth;
    private Long soLuong;
    private BigDecimal ttlSettlementAmount;
    private String transGroup;
    private String date;
    private String errorCode;
    private String refusalCode;

    private Date convertDate;

    private Long lateRespond;

    public PaymentStatistic() {
    }

    public PaymentStatistic(String instructingAgent, String instructedAgent, String ttc, String transType,
                            String channel, String creditorType, String status, String auth, String errorCode,
                            String refusalCode, Long soLuong, BigDecimal ttlSettlementAmount, Long lateRespond) {
        this.instructingAgent = instructingAgent;
        this.instructedAgent = instructedAgent;
        this.ttc = ttc;
        this.transType = transType;
        this.channel = channel;
        this.creditorType = creditorType;
        this.status = status;
        this.auth = auth;
        this.errorCode = errorCode;
        this.refusalCode = refusalCode;
        this.soLuong = soLuong;
        this.ttlSettlementAmount = ttlSettlementAmount;
        this.lateRespond = lateRespond;
    }

    public String getInstructingAgent() {
        return instructingAgent;
    }

    public void setInstructingAgent(String instructingAgent) {
        this.instructingAgent = instructingAgent;
    }

    public String getInstructedAgent() {
        return instructedAgent;
    }

    public void setInstructedAgent(String instructedAgent) {
        this.instructedAgent = instructedAgent;
    }

    public String getTtc() {
        return ttc;
    }

    public void setTtc(String ttc) {
        this.ttc = ttc;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCreditorType() {
        return creditorType;
    }

    public void setCreditorType(String creditorType) {
        this.creditorType = creditorType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getTtlSettlementAmount() {
        return ttlSettlementAmount;
    }

    public void setTtlSettlementAmount(BigDecimal ttlSettlementAmount) {
        this.ttlSettlementAmount = ttlSettlementAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransGroup() {
        return transGroup;
    }

    public void setTransGroup(String transGroup) {
        this.transGroup = transGroup;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getRefusalCode() {
        return refusalCode;
    }

    public void setRefusalCode(String refusalCode) {
        this.refusalCode = refusalCode;
    }

    public Date getConvertDate() {
        return convertDate;
    }

    public void setConvertDate(Date convertDate) {
        this.convertDate = convertDate;
    }

    public Long getLateRespond() {
        return lateRespond;
    }

    public void setLateRespond(Long lateRespond) {
        this.lateRespond = lateRespond;
    }
}
