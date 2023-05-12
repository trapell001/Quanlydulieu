/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "TBL_RTP_TARIFF_LADDER")
@NamedQueries({
        @NamedQuery(name = "TblRtpTariffLadder.findAll", query = "SELECT t FROM TblRtpTariffLadder t")})
public class TblRtpTariffLadder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TARIFF_LADDER")
    @SequenceGenerator(sequenceName = "SEQ_TARIFF_LADDER", allocationSize = 1, name = "SEQ_TARIFF_LADDER")
    @Column(name = "TARIFF_LADDER_ID")
    private Integer tariffLadderId;
    @Column(name = "TARIFF_PLAN_ID")
    private Integer tariffPlanId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MIN_NUM_TRANS")
    private long minNumTrans;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_NUM_TRANS")
    private long maxNumTrans;
    @Size(max = 500)
    @Column(name = "TARIFF_LADDER_DESCRIPTION")
    private String tariffLadderDescription;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "DATE_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

    public TblRtpTariffLadder() {
    }

    public TblRtpTariffLadder(Integer tariffLadderId) {
        this.tariffLadderId = tariffLadderId;
    }

    public TblRtpTariffLadder(Integer tariffLadderId, long minNumTrans, long maxNumTrans) {
        this.tariffLadderId = tariffLadderId;
        this.minNumTrans = minNumTrans;
        this.maxNumTrans = maxNumTrans;
    }

    public Integer getTariffLadderId() {
        return tariffLadderId;
    }

    public void setTariffLadderId(Integer tariffLadderId) {
        this.tariffLadderId = tariffLadderId;
    }

    public Integer getTariffPlanId() {
        return tariffPlanId;
    }

    public void setTariffPlanId(Integer tariffPlanId) {
        this.tariffPlanId = tariffPlanId;
    }

    public long getMinNumTrans() {
        return minNumTrans;
    }

    public void setMinNumTrans(long minNumTrans) {
        this.minNumTrans = minNumTrans;
    }

    public long getMaxNumTrans() {
        return maxNumTrans;
    }

    public void setMaxNumTrans(long maxNumTrans) {
        this.maxNumTrans = maxNumTrans;
    }

    public String getTariffLadderDescription() {
        return tariffLadderDescription;
    }

    public void setTariffLadderDescription(String tariffLadderDescription) {
        this.tariffLadderDescription = tariffLadderDescription;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tariffLadderId != null ? tariffLadderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRtpTariffLadder)) {
            return false;
        }
        TblRtpTariffLadder other = (TblRtpTariffLadder) object;
        if ((this.tariffLadderId == null && other.tariffLadderId != null) || (this.tariffLadderId != null && !this.tariffLadderId.equals(other.tariffLadderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.models.TblRtpTariffLadder[ tariffLadderId=" + tariffLadderId + " ]";
    }

}
