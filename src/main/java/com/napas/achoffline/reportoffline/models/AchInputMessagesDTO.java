/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author HuyNX
 */
public class AchInputMessagesDTO {
    private Long messageID;
    private String sender;
    private String msgType;
    private String rawMsg;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date acceptDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date modifDate;
    private String pie;
    private String mxMsgID;
    private String mxOrigMsgID;
    private BigInteger operdayID;
    private String resultDesc;

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getRawMsg() {
        return rawMsg;
    }

    public void setRawMsg(String rawMsg) {
        this.rawMsg = rawMsg;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public Date getModifDate() {
        return modifDate;
    }

    public void setModifDate(Date modifDate) {
        this.modifDate = modifDate;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    public String getMxMsgID() {
        return mxMsgID;
    }

    public void setMxMsgID(String mxMsgID) {
        this.mxMsgID = mxMsgID;
    }

    public String getMxOrigMsgID() {
        return mxOrigMsgID;
    }

    public void setMxOrigMsgID(String mxOrigMsgID) {
        this.mxOrigMsgID = mxOrigMsgID;
    }

    public BigInteger getOperdayID() {
        return operdayID;
    }

    public void setOperdayID(BigInteger operdayID) {
        this.operdayID = operdayID;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

}
