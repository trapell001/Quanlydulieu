/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.define;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author HuyNX
 */
@Entity
@Table(name = "INPUT_MESSAGES", schema="ACH")
@NamedQueries({
        @NamedQuery(name = "AchInputMessages.findAll", query = "SELECT a FROM AchInputMessages a")})
public class AchInputMessages implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MESSAGE_ID")
    private Long messageID;

    @Size(max = 12)
    @Column(name = "UID_")
    private String uid;
    @Size(max = 128)
    @Column(name = "SID_")
    private String sid;
    @Size(max = 50)
    @Column(name = "TYPE_DOC")
    private String typeDoc;
    @Column(name = "MSG_FORMAT")
    private Character msgFormat;
    @Column(name = "MSG_SUB_FORMAT")
    private Character msgSubFormat;
    @Size(max = 12)
    @Column(name = "MSG_SENDER")
    private String sender;

    @Size(max = 12)
    @Column(name = "MSG_RECEIVER")
    private String msgReceiver;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MSG_TYPE")
    private String msgType;

    @Column(name = "MSG_PRIORITY")
    private Character msgPriority;
    @Column(name = "MSG_DEL_NOTIF_RQ")
    private Character msgDelNotifRq;
    @Size(max = 4)
    @Column(name = "MSG_USER_PRIORITY")
    private String msgUserPriority;
    @Size(max = 35)
    @Column(name = "MSG_USER_REFERENCE")
    private String msgUserReference;
    @Size(max = 3)
    @Column(name = "MSG_COPY_SRV_ID")
    private String msgCopySrvId;
    @Size(max = 8)
    @Column(name = "MSG_FIN_VALIDATION")
    private String msgFinValidation;
    @Column(name = "MSG_PDE")
    private Character msgPde;
    @Size(max = 4)
    @Column(name = "MSG_SESSION")
    private String msgSession;
    @Size(max = 6)
    @Column(name = "MSG_SEQUENCE")
    private String msgSequence;
    @Size(max = 4)
    @Column(name = "MSG_NET_INPUT_TIME")
    private String msgNetInputTime;
    @Size(max = 10)
    @Column(name = "MSG_NET_OUTPUT_DATE")
    private String msgNetOutputDate;
    @Size(max = 28)
    @Column(name = "MSG_NET_MIR")
    private String msgNetMir;
    @Size(max = 32)
    @Column(name = "MSG_COPY_SRV_INFO")
    private String msgCopySrvInfo;
    @Size(max = 4000)
    @Column(name = "MSG_MAC_RESULT")
    private String msgMacResult;
    @Size(max = 4000)
    @Column(name = "MSG_PAC_RESULT")
    private String msgPacResult;
    @Column(name = "MSG_PDM")
    private Character msgPdm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SIZE_MORE_2000")
    private Character sizeMore2000;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MSG_STATUS")
    private Character msgStatus;
    @Size(max = 4000)
    @Column(name = "BLOCK4")
    private String block4;

    @Lob
    @Column(name = "BLOCK4_LARGE", columnDefinition="BLOB")
    private byte[] block4Large;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MODIF_USER_ID")
    private long modifUserId;
    @Column(name = "MODIF_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifDate;
    @Column(name = "RESULT_ID")
    private Long resultId;
    @Size(max = 32)
    @Column(name = "MSG_TRAN_TYPE_CODE")
    private String msgTranTypeCode;
    @Column(name = "MSG_AMOUNT")
    private BigDecimal msgAmount;
    @Column(name = "MSG_NUM_OF_BATCHES")
    private BigInteger msgNumOfBatches;
    @Column(name = "OPERDAY_ID")
    private BigInteger operdayID;
    @Column(name = "ACCEPT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptDate;
    @Column(name = "EVENT_ID")
    private BigInteger eventId;
    @Size(max = 12)
    @Column(name = "ORG_INPUT_MSG_SENDER")
    private String orgInputMsgSender;
    @Size(max = 6)
    @Column(name = "PIE")
    private String pie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CHANNEL")
    private String channel;
    @Column(name = "MX_SIGN")
    private Character mxSign;
    @Size(max = 35)
    @Column(name = "MX_SENDER_REF")
    private String mxSenderRef;
    @Size(max = 1000)
    @Column(name = "XSD_FILENAME")
    private String xsdFilename;
    @Size(max = 35)
    @Column(name = "BIZ_SVC")
    private String bizSvc;
    @Column(name = "M_CHANGE_ID")
    private Long mChangeId;
    @Column(name = "M_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mDate;
    @Size(max = 39)
    @Column(name = "MSG_INFO_SRV_INFO")
    private String msgInfoSrvInfo;
    @Size(max = 25)
    @Column(name = "MSG_SANC_SCREEN_SRV_INFO")
    private String msgSancScreenSrvInfo;
    @Size(max = 14)
    @Column(name = "MSG_BAL_CHECKPOINT_DT")
    private String msgBalCheckpointDt;
    @Size(max = 28)
    @Column(name = "MSG_RELATED_MIR")
    private String msgRelatedMir;
    @Size(max = 16)
    @Column(name = "MSG_RELATED_REF")
    private String msgRelatedRef;
    @Size(max = 3)
    @Column(name = "MSG_SVC_TYPE_ID")
    private String msgSvcTypeId;
    @Size(max = 36)
    @Column(name = "MSG_UNIQUE_E2E_REF")
    private String msgUniqueE2eRef;
    @Size(max = 4000)
    @Column(name = "RESULT_DESC")
    private String resultDesc;
    @Size(max = 35)
    @Column(name = "MX_MSGID")
    private String mxMsgID;
    @Size(max = 35)
    @Column(name = "MX_ORIG_MSGID")
    private String mxOrigMsgID;
    @Column(name = "CRE_DT_TM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creDtTm;

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTypeDoc() {
        return typeDoc;
    }

    public void setTypeDoc(String typeDoc) {
        this.typeDoc = typeDoc;
    }

    public Character getMsgFormat() {
        return msgFormat;
    }

    public void setMsgFormat(Character msgFormat) {
        this.msgFormat = msgFormat;
    }

    public Character getMsgSubFormat() {
        return msgSubFormat;
    }

    public void setMsgSubFormat(Character msgSubFormat) {
        this.msgSubFormat = msgSubFormat;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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

    public Character getMsgPriority() {
        return msgPriority;
    }

    public void setMsgPriority(Character msgPriority) {
        this.msgPriority = msgPriority;
    }

    public Character getMsgDelNotifRq() {
        return msgDelNotifRq;
    }

    public void setMsgDelNotifRq(Character msgDelNotifRq) {
        this.msgDelNotifRq = msgDelNotifRq;
    }

    public String getMsgUserPriority() {
        return msgUserPriority;
    }

    public void setMsgUserPriority(String msgUserPriority) {
        this.msgUserPriority = msgUserPriority;
    }

    public String getMsgUserReference() {
        return msgUserReference;
    }

    public void setMsgUserReference(String msgUserReference) {
        this.msgUserReference = msgUserReference;
    }

    public String getMsgCopySrvId() {
        return msgCopySrvId;
    }

    public void setMsgCopySrvId(String msgCopySrvId) {
        this.msgCopySrvId = msgCopySrvId;
    }

    public String getMsgFinValidation() {
        return msgFinValidation;
    }

    public void setMsgFinValidation(String msgFinValidation) {
        this.msgFinValidation = msgFinValidation;
    }

    public Character getMsgPde() {
        return msgPde;
    }

    public void setMsgPde(Character msgPde) {
        this.msgPde = msgPde;
    }

    public String getMsgSession() {
        return msgSession;
    }

    public void setMsgSession(String msgSession) {
        this.msgSession = msgSession;
    }

    public String getMsgSequence() {
        return msgSequence;
    }

    public void setMsgSequence(String msgSequence) {
        this.msgSequence = msgSequence;
    }

    public String getMsgNetInputTime() {
        return msgNetInputTime;
    }

    public void setMsgNetInputTime(String msgNetInputTime) {
        this.msgNetInputTime = msgNetInputTime;
    }

    public String getMsgNetOutputDate() {
        return msgNetOutputDate;
    }

    public void setMsgNetOutputDate(String msgNetOutputDate) {
        this.msgNetOutputDate = msgNetOutputDate;
    }

    public String getMsgNetMir() {
        return msgNetMir;
    }

    public void setMsgNetMir(String msgNetMir) {
        this.msgNetMir = msgNetMir;
    }

    public String getMsgCopySrvInfo() {
        return msgCopySrvInfo;
    }

    public void setMsgCopySrvInfo(String msgCopySrvInfo) {
        this.msgCopySrvInfo = msgCopySrvInfo;
    }

    public String getMsgMacResult() {
        return msgMacResult;
    }

    public void setMsgMacResult(String msgMacResult) {
        this.msgMacResult = msgMacResult;
    }

    public String getMsgPacResult() {
        return msgPacResult;
    }

    public void setMsgPacResult(String msgPacResult) {
        this.msgPacResult = msgPacResult;
    }

    public Character getMsgPdm() {
        return msgPdm;
    }

    public void setMsgPdm(Character msgPdm) {
        this.msgPdm = msgPdm;
    }

    public Character getSizeMore2000() {
        return sizeMore2000;
    }

    public void setSizeMore2000(Character sizeMore2000) {
        this.sizeMore2000 = sizeMore2000;
    }

    public Character getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(Character msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getBlock4() {
        return block4;
    }

    public void setBlock4(String block4) {
        this.block4 = block4;
    }

    public byte[] getBlock4Large() {
        return block4Large;
    }

    public void setBlock4Large(byte[] block4Large) {
        this.block4Large = block4Large;
    }

    public long getModifUserId() {
        return modifUserId;
    }

    public void setModifUserId(long modifUserId) {
        this.modifUserId = modifUserId;
    }

    public Date getModifDate() {
        return modifDate;
    }

    public void setModifDate(Date modifDate) {
        this.modifDate = modifDate;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getMsgTranTypeCode() {
        return msgTranTypeCode;
    }

    public void setMsgTranTypeCode(String msgTranTypeCode) {
        this.msgTranTypeCode = msgTranTypeCode;
    }

    public BigDecimal getMsgAmount() {
        return msgAmount;
    }

    public void setMsgAmount(BigDecimal msgAmount) {
        this.msgAmount = msgAmount;
    }

    public BigInteger getMsgNumOfBatches() {
        return msgNumOfBatches;
    }

    public void setMsgNumOfBatches(BigInteger msgNumOfBatches) {
        this.msgNumOfBatches = msgNumOfBatches;
    }

    public BigInteger getOperdayID() {
        return operdayID;
    }

    public void setOperdayID(BigInteger operdayID) {
        this.operdayID = operdayID;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public BigInteger getEventId() {
        return eventId;
    }

    public void setEventId(BigInteger eventId) {
        this.eventId = eventId;
    }

    public String getOrgInputMsgSender() {
        return orgInputMsgSender;
    }

    public void setOrgInputMsgSender(String orgInputMsgSender) {
        this.orgInputMsgSender = orgInputMsgSender;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Character getMxSign() {
        return mxSign;
    }

    public void setMxSign(Character mxSign) {
        this.mxSign = mxSign;
    }

    public String getMxSenderRef() {
        return mxSenderRef;
    }

    public void setMxSenderRef(String mxSenderRef) {
        this.mxSenderRef = mxSenderRef;
    }

    public String getXsdFilename() {
        return xsdFilename;
    }

    public void setXsdFilename(String xsdFilename) {
        this.xsdFilename = xsdFilename;
    }

    public String getBizSvc() {
        return bizSvc;
    }

    public void setBizSvc(String bizSvc) {
        this.bizSvc = bizSvc;
    }

    public Long getmChangeId() {
        return mChangeId;
    }

    public void setmChangeId(Long mChangeId) {
        this.mChangeId = mChangeId;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getMsgInfoSrvInfo() {
        return msgInfoSrvInfo;
    }

    public void setMsgInfoSrvInfo(String msgInfoSrvInfo) {
        this.msgInfoSrvInfo = msgInfoSrvInfo;
    }

    public String getMsgSancScreenSrvInfo() {
        return msgSancScreenSrvInfo;
    }

    public void setMsgSancScreenSrvInfo(String msgSancScreenSrvInfo) {
        this.msgSancScreenSrvInfo = msgSancScreenSrvInfo;
    }

    public String getMsgBalCheckpointDt() {
        return msgBalCheckpointDt;
    }

    public void setMsgBalCheckpointDt(String msgBalCheckpointDt) {
        this.msgBalCheckpointDt = msgBalCheckpointDt;
    }

    public String getMsgRelatedMir() {
        return msgRelatedMir;
    }

    public void setMsgRelatedMir(String msgRelatedMir) {
        this.msgRelatedMir = msgRelatedMir;
    }

    public String getMsgRelatedRef() {
        return msgRelatedRef;
    }

    public void setMsgRelatedRef(String msgRelatedRef) {
        this.msgRelatedRef = msgRelatedRef;
    }

    public String getMsgSvcTypeId() {
        return msgSvcTypeId;
    }

    public void setMsgSvcTypeId(String msgSvcTypeId) {
        this.msgSvcTypeId = msgSvcTypeId;
    }

    public String getMsgUniqueE2eRef() {
        return msgUniqueE2eRef;
    }

    public void setMsgUniqueE2eRef(String msgUniqueE2eRef) {
        this.msgUniqueE2eRef = msgUniqueE2eRef;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
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

    public Date getCreDtTm() {
        return creDtTm;
    }

    public void setCreDtTm(Date creDtTm) {
        this.creDtTm = creDtTm;
    }


}
