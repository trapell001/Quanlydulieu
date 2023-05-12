/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;
import com.napas.achoffline.reportoffline.define.QtbsPaymentType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 *
 */
public class TblPaymentsQtbsDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date dateCreated;
    private Long sessionId;
    private QtbsPaymentType msgType;
    private String txid;
    private String description;
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date origProcessDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date origTransDate;

    private String origMsgType;
    private Long origSessionId;
    private String origTransStatus;
    private String origAuthInfo;
    private BigDecimal origSettlementAmount;
    private String origInstructingAgent;
    private String origInstructedAgent;
    private QtbsPaymentStatus status;
    private String userMaker;
    private String userChecker;
    private BigDecimal returnAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date dateApproved;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public QtbsPaymentType getMsgType() {
        return msgType;
    }

    public void setMsgType(QtbsPaymentType msgType) {
        this.msgType = msgType;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrigProcessDate() {
        return origProcessDate;
    }

    public void setOrigProcessDate(Date origProcessDate) {
        this.origProcessDate = origProcessDate;
    }

    public Long getOrigSessionId() {
        return origSessionId;
    }

    public void setOrigSessionId(Long origSessionId) {
        this.origSessionId = origSessionId;
    }

    public String getOrigTransStatus() {
        return origTransStatus;
    }

    public void setOrigTransStatus(String origTransStatus) {
        this.origTransStatus = origTransStatus;
    }

    public String getOrigAuthInfo() {
        return origAuthInfo;
    }

    public void setOrigAuthInfo(String origAuthInfo) {
        this.origAuthInfo = origAuthInfo;
    }

    public BigDecimal getOrigSettlementAmount() {
        return origSettlementAmount;
    }

    public void setOrigSettlementAmount(BigDecimal origSettlementAmount) {
        this.origSettlementAmount = origSettlementAmount;
    }

    public String getOrigInstructingAgent() {
        return origInstructingAgent;
    }

    public void setOrigInstructingAgent(String origInstructingAgent) {
        this.origInstructingAgent = origInstructingAgent;
    }

    public String getOrigInstructedAgent() {
        return origInstructedAgent;
    }

    public void setOrigInstructedAgent(String origInstructedAgent) {
        this.origInstructedAgent = origInstructedAgent;
    }

    public QtbsPaymentStatus getStatus() {
        return status;
    }

    public void setStatus(QtbsPaymentStatus status) {
        this.status = status;
    }

    public String getUserMaker() {
        return userMaker;
    }

    public void setUserMaker(String userMaker) {
        this.userMaker = userMaker;
    }

    public String getUserChecker() {
        return userChecker;
    }

    public void setUserChecker(String userChecker) {
        this.userChecker = userChecker;
    }

    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getOrigMsgType() {
        return origMsgType;
    }

    public void setOrigMsgType(String origMsgType) {
        this.origMsgType = origMsgType;
    }

    public Date getOrigTransDate() {
        return origTransDate;
    }

    public void setOrigTransDate(Date origTransDate) {
        this.origTransDate = origTransDate;
    }

    public TblPaymentsQtbsDTO() {
    }

    public TblPaymentsQtbsDTO(Date dateCreated, Long sessionId, QtbsPaymentType msgType, String txid, String description, Integer id, Date origProcessDate, OffsetDateTime origTransDate, String origMsgType, Long origSessionId, String origTransStatus, String origAuthInfo, BigDecimal origSettlementAmount, String origInstructingAgent, String origInstructedAgent, QtbsPaymentStatus status, String userMaker, String userChecker, BigDecimal returnAmount, Date dateApproved) {
        this.dateCreated = dateCreated;
        this.sessionId = sessionId;
        this.msgType = msgType;
        this.txid = txid;
        this.description = description;
        this.id = id;
        this.origProcessDate = origProcessDate;
        this.origTransDate = new Date(origTransDate.toInstant().toEpochMilli());
        this.origMsgType = origMsgType;
        this.origSessionId = origSessionId;
        this.origTransStatus = origTransStatus;
        this.origAuthInfo = origAuthInfo;
        this.origSettlementAmount = origSettlementAmount;
        this.origInstructingAgent = origInstructingAgent;
        this.origInstructedAgent = origInstructedAgent;
        this.status = status;
        this.userMaker = userMaker;
        this.userChecker = userChecker;
        this.returnAmount = returnAmount;
        this.dateApproved = dateApproved;
    }

    public TblPaymentsQtbsDTO(Date dateCreated, Long sessionId, QtbsPaymentType msgType, String txid, String description, Integer id, QtbsPaymentStatus status, String userMaker, String userChecker, BigDecimal returnAmount, Date dateApproved) {
        this.dateCreated = dateCreated;
        this.sessionId = sessionId;
        this.msgType = msgType;
        this.txid = txid;
        this.description = description;
        this.id = id;
        this.status = status;
        this.userMaker = userMaker;
        this.userChecker = userChecker;
        this.returnAmount = returnAmount;
        this.dateApproved = dateApproved;
    }
}
