/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;
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
@Table(name = "TBL_FEE_QTBS")
@NamedQueries({
    @NamedQuery(name = "TblFeeQtbs.findAll", query = "SELECT t FROM TblFeeQtbs t")})
public class TblFeeQtbs implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "BIC")
    private String bic;
    @Column(name = "SESSION_ID")
    private Long sessionId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "DEBIT_AMOUNT")
    private BigDecimal debitAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDIT_AMOUNT")
    private BigDecimal creditAmount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Size(max = 500)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    @Enumerated(EnumType.ORDINAL)
    private QtbsPaymentStatus status;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 50)
    @Column(name = "USER_MAKER")
    private String userMaker;
    @Size(min = 1, max = 50)
    @Column(name = "USER_CHECKER")
    private String userChecker;
    @Column(name = "DATE_APPROVED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateApproved;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FEE_QTBS")
    @SequenceGenerator(sequenceName = "SEQ_FEE_QTBS", allocationSize = 1, name = "SEQ_FEE_QTBS")
    @Column(name = "ID")
    private Long id;

    public TblFeeQtbs() {
    }

    public TblFeeQtbs(Long id) {
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
        if (!(object instanceof TblFeeQtbs)) {
            return false;
        }
        TblFeeQtbs other = (TblFeeQtbs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.models.TblFeeQtbs[ id=" + id + " ]";
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
