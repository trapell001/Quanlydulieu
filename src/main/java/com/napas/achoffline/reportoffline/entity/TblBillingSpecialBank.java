/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
@Table(name = "TBL_BILLING_SPECIAL_BANK")
@NamedQueries({
    @NamedQuery(name = "TblBillingSpecialBank.findAll", query = "SELECT t FROM TblBillingSpecialBank t")})
public class TblBillingSpecialBank implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BILLING_SPECIAL_BANK")
    @SequenceGenerator(sequenceName = "SEQ_BILLING_SPECIAL_BANK", allocationSize = 1, name = "SEQ_BILLING_SPECIAL_BANK")
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "BIC")
    private String bic;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DATE_CREADTED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreadted;

    public TblBillingSpecialBank() {
    }

    public TblBillingSpecialBank(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblBillingSpecialBank)) {
            return false;
        }
        TblBillingSpecialBank other = (TblBillingSpecialBank) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.entity.TblBillingSpecialBank[ id=" + id + " ]";
    }

}
