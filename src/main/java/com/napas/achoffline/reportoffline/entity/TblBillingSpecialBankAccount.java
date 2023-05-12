/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TBL_BILLING_SPECIAL_BANK_ACCOUNT")
@NamedQueries({
    @NamedQuery(name = "TblBillingSpecialBankAccount.findAll", query = "SELECT t FROM TblBillingSpecialBankAccount t")})
public class TblBillingSpecialBankAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BILLING_SPECIAL_BANK_ACCOUNT")
    @SequenceGenerator(sequenceName = "SEQ_BILLING_SPECIAL_BANK_ACCOUNT", allocationSize = 1, name = "SEQ_BILLING_SPECIAL_BANK_ACCOUNT")
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "BIC")
    private String bic;
    @Size(max = 20)
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Size(max = 20)
    @Column(name = "DIRECTION")
    private String direction;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DATE_CREADTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreadted;
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FEE_AMOUNT")
    private BigDecimal feeAmount;
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;

    @Column(name = "CAMPAIGN_ID")
    private Long campaignId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public TblBillingSpecialBankAccount() {
    }

    public TblBillingSpecialBankAccount(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreadted() {
        return dateCreadted;
    }

    public void setDateCreadted(Date dateCreadted) {
        this.dateCreadted = dateCreadted;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
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
        if (!(object instanceof TblBillingSpecialBankAccount)) {
            return false;
        }
        TblBillingSpecialBankAccount other = (TblBillingSpecialBankAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.models.TblBillingSpecialBankAccount[ id=" + id + " ]";
    }

}
