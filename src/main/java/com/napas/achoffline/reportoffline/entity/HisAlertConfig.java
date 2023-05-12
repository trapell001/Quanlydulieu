

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author huynx
 */
//them phan hisalertconfig
@Entity
@Table(name = "HIS_ALERT_CONFIG")
@NamedQueries({
        @NamedQuery(name = "HisAlertConfig.findAll", query = "SELECT h FROM HisAlertConfig h")})
public class HisAlertConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ALERT_CODE")
    private String alertCode;
    @Column(name = "ALERT_NAME")
    private String alertName;

    @Column(name = "ALERT_TITLE")
    private String alertTitle;

    @Column(name = "ALERT_COMMENT")
    private String alertComment;
    @Column(name = "ALERT_TYPE")
    private Long alertType;
    @Column(name = "CREATED_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "MODIFI_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiDate;

    public HisAlertConfig() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlertCode() {
        return alertCode;
    }

    public void setAlertCode(String alertCode) {
        this.alertCode = alertCode;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alerrTitle) {
        this.alertTitle = alerrTitle;
    }

    public String getAlertComment() {
        return alertComment;
    }

    public void setAlertComment(String alertComment) {
        this.alertComment = alertComment;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiDate() {
        return modifiDate;
    }

    public void setModifiDate(Date modifiDate) {
        this.modifiDate = modifiDate;
    }

    public Long getAlertType() {
        return alertType;
    }

    public void setAlertType(Long alertType) {
        this.alertType = alertType;
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
        if (!(object instanceof HisAlertConfig)) {
            return false;
        }
        HisAlertConfig other = (HisAlertConfig) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.entity.HisAlertConfig[ id=" + id + " ]";
    }

}