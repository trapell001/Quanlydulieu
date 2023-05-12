/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "TBL_ACH_BANK")
@NamedQueries({
    @NamedQuery(name = "TblAchBank.findAll", query = "SELECT t FROM TblAchBank t")})
public class TblAchBank implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 6)
    @Column(name = "BANK_ID")
    private String bankId;
    @Size(max = 32)
    @Column(name = "BANK_CODE")
    private String bankCode;
    @Size(max = 64)
    @Column(name = "BANK_NAME")
    private String bankName;
    @Size(max = 100)
    @Column(name = "FULL_NAME")
    private String fullName;
    @Size(max = 128)
    @Column(name = "FULL_NAME_ENG")
    private String fullNameEng;
    @Size(max = 32)
    @Column(name = "BIC_CODE_SWIFT")
    private String bicCodeSwift;
    @Size(max = 6)
    @Column(name = "BANK_ID_CITAD")
    private String bankIdCitad;
    @Size(max = 32)
    @Column(name = "SECRET_KEY")
    private String secretKey;
    @Size(max = 50)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 50)
    @Column(name = "CREATED_USER")
    private String createdUser;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 50)
    @Column(name = "UPDATED_USER")
    private String updatedUser;
    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @OneToMany(mappedBy = "achBankId")
    private Collection<TblAchBankParticipants> tblAchBankParticipantsCollection;

    public TblAchBank() {
    }

    public TblAchBank(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameEng() {
        return fullNameEng;
    }

    public void setFullNameEng(String fullNameEng) {
        this.fullNameEng = fullNameEng;
    }

    public String getBicCodeSwift() {
        return bicCodeSwift;
    }

    public void setBicCodeSwift(String bicCodeSwift) {
        this.bicCodeSwift = bicCodeSwift;
    }

    public String getBankIdCitad() {
        return bankIdCitad;
    }

    public void setBankIdCitad(String bankIdCitad) {
        this.bankIdCitad = bankIdCitad;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Collection<TblAchBankParticipants> getTblAchBankParticipantsCollection() {
        return tblAchBankParticipantsCollection;
    }

    public void setTblAchBankParticipantsCollection(Collection<TblAchBankParticipants> tblAchBankParticipantsCollection) {
        this.tblAchBankParticipantsCollection = tblAchBankParticipantsCollection;
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
        if (!(object instanceof TblAchBank)) {
            return false;
        }
        TblAchBank other = (TblAchBank) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.entity.TblAchBank[ id=" + id + " ]";
    }

}
