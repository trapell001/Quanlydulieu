/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;
import com.napas.achoffline.reportoffline.define.QtbsPaymentType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author huynx
 */
@Entity
@Table(name = "TBL_PAYMENTS_QTBS")
@NamedQueries({
    @NamedQuery(name = "TblPaymentsQtbs.findAll", query = "SELECT t FROM TblPaymentsQtbs t")})
public class TblPaymentsQtbs implements Serializable {

    @Column(name = "SESSION_ID")
    private Long sessionId;
    @Basic(optional = false)
    @NotNull

    @Column(name = "MSG_TYPE")
    @Enumerated(EnumType.STRING)
    private QtbsPaymentType msgType;
    @Basic(optional = false)
    @NotNull

    @Column(name = "TXID")
    private String txid;

    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    @Enumerated(EnumType.ORDINAL)
    private QtbsPaymentStatus status;
    @Basic(optional = false)
    @NotNull

    @Column(name = "USER_MAKER")
    private String userMaker;


    @Column(name = "USER_CHECKER")
    private String userChecker;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "RETURN_AMOUNT")
    private BigDecimal returnAmount;
    @Column(name = "DATE_APPROVED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateApproved;

    private static final long serialVersionUID = 1L;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAYMENTS_QTBS")
    @SequenceGenerator(sequenceName = "SEQ_PAYMENTS_QTBS", allocationSize = 1, name = "SEQ_PAYMENTS_QTBS")
    @Column(name = "ID")
    private Integer id;

    public TblPaymentsQtbs() {
    }

    public TblPaymentsQtbs(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPaymentsQtbs)) {
            return false;
        }
        TblPaymentsQtbs other = (TblPaymentsQtbs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.entity.TblPaymentsQtbs[ id=" + id + " ]";
    }

    public TblPaymentsQtbs(Long sessionId, QtbsPaymentType msgType, String txid, String description, QtbsPaymentStatus status, String userMaker, String userChecker, BigDecimal returnAmount, Date dateApproved, Date dateCreated, Integer id) {
        this.sessionId = sessionId;
        this.msgType = msgType;
        this.txid = txid;
        this.description = description;
        this.status = status;
        this.userMaker = userMaker;
        this.userChecker = userChecker;
        this.returnAmount = returnAmount;
        this.dateApproved = dateApproved;
        this.dateCreated = dateCreated;
        this.id = id;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
