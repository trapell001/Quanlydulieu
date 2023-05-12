package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author HuyNX
 */
public class AchOutputMessagesDTO {
    private Long messageID;
    private Long inputMessageID;
    private String msgReceiver;
    private String msgType;
    private String rawMsg;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date modifDate;
    private BigInteger operdayID;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Ho_Chi_Minh")
    private Date creationTime;

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public Long getInputMessageID() {
        return inputMessageID;
    }

    public void setInputMessageID(Long inputMessageID) {
        this.inputMessageID = inputMessageID;
    }

    public String getMsgReceiver() {
        return msgReceiver;
    }

    public void setMsgReceiver(String msgReceiver) {
        this.msgReceiver = msgReceiver;
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

    public Date getModifDate() {
        return modifDate;
    }

    public void setModifDate(Date modifDate) {
        this.modifDate = modifDate;
    }

    public BigInteger getOperdayID() {
        return operdayID;
    }

    public void setOperdayID(BigInteger operdayID) {
        this.operdayID = operdayID;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }


}
