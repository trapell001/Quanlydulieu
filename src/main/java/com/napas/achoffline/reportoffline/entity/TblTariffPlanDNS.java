/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import com.napas.achoffline.reportoffline.define.ReturnPaymentFeeType;
import java.io.Serializable;
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

/**
 *
 * @author huynx
 */
@Entity
@Table(name = "TBL_TARIFF_PLAN_DNS") ///DNS
@NamedQueries({
        @NamedQuery(name = "TblTariffPlanDns.findAll", query = "SELECT t FROM TblTariffPlanDNS t")})
public class TblTariffPlanDNS implements Serializable {

    @Basic(optional = false)
    @NotNull
    // @Size(min = 1, max = 100)
    @Column(name = "PLAN_NAME")
    private String planName;
    //  @Size(max = 1000)
    @Column(name = "PLAN_DESCRIPTION")
    private String planDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LADDER_ENABLED_AMOUNT")
    private long ladderEnabledAmount;
    @Basic(optional = false)
    @NotNull
    //  @Size(min = 1, max = 20)
    @Column(name = "RETURN_PAYMENT_FEE_TYPE")
    @Enumerated(EnumType.STRING)
    private ReturnPaymentFeeType returnPaymentFeeType;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TARIFF_PLAN")
    @SequenceGenerator(sequenceName = "SEQ_TARIFF_PLAN", allocationSize = 1, name = "SEQ_TARIFF_PLAN")
    @Column(name = "TARIFF_PLAN_ID")
    private Integer tariffPlanId;
    @Column(name = "DATE_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;
    @Column(name = "DATE_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

    @Column(name = "TARIFF_PLAN_CODE")
    private String tariffPlanCode;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTariffPlanCode() {
        return tariffPlanCode;
    }

    public void setTariffPlanCode(String tariffPlanCode) {
        this.tariffPlanCode = tariffPlanCode;
    }

    public TblTariffPlanDNS() {
    }

    public TblTariffPlanDNS(Integer tariffPlanId) {
        this.tariffPlanId = tariffPlanId;
    }

    public TblTariffPlanDNS(String planName, String planDescription, long ladderEnabledAmount, ReturnPaymentFeeType returnPaymentFeeType, Integer tariffPlanId, Date dateCreate, Date dateModified, String tariffPlanCode) {
        this.planName = planName;
        this.planDescription = planDescription;
        this.ladderEnabledAmount = ladderEnabledAmount;
        this.returnPaymentFeeType = returnPaymentFeeType;
        this.tariffPlanId = tariffPlanId;
        this.dateCreate = dateCreate;
        this.dateModified = dateModified;
        this.tariffPlanCode= tariffPlanCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tariffPlanId != null ? tariffPlanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblTariffPlanDNS)) {
            return false;
        }
        TblTariffPlanDNS other = (TblTariffPlanDNS) object;
        if ((this.tariffPlanId == null && other.tariffPlanId != null) || (this.tariffPlanId != null && !this.tariffPlanId.equals(other.tariffPlanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.models.TblTariffPlan[ tariffPlanId=" + tariffPlanId + " ]";
    }

    public Integer getTariffPlanId() {
        return tariffPlanId;
    }

    public void setTariffPlanId(Integer tariffPlanId) {
        this.tariffPlanId = tariffPlanId;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public long getLadderEnabledAmount() {
        return ladderEnabledAmount;
    }

    public void setLadderEnabledAmount(long ladderEnabledAmount) {
        this.ladderEnabledAmount = ladderEnabledAmount;
    }

    public ReturnPaymentFeeType getReturnPaymentFeeType() {
        return returnPaymentFeeType;
    }

    public void setReturnPaymentFeeType(ReturnPaymentFeeType returnPaymentFeeType) {
        this.returnPaymentFeeType = returnPaymentFeeType;
    }

}
