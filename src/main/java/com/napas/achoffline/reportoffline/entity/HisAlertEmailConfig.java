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
@Table(name = "HIS_ALERT_EMAIL_CONFIG")
@NamedQueries({
        @NamedQuery(name = "HisAlertEmailConfig.findAll", query = "SELECT h FROM HisAlertEmailConfig h")})
public class HisAlertEmailConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ALERT_CODE")
    private String alertCode;

    @Column(name = "EMAIL_SEND")
    private String emailSend;

    @Column(name = "EMAIL_RECEIVE")
    private String emailReceive;

    @Column(name = "PARTICIPANT")
    private String participant;
    @Column(name = "ALERT_TYPE")
    private Long alertType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "MODIFI_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiDate;

    public HisAlertEmailConfig() {

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

    public String getEmailSend() {
        return emailSend;
    }

    public void setEmailSend(String emailSend) {
        this.emailSend = emailSend;
    }

    public String getEmailReceive() {
        return emailReceive;
    }

    public void setEmailReceive(String emailReceive) {
        this.emailReceive = emailReceive;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
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
        if (!(object instanceof HisAlertEmailConfig)) {
            return false;
        }
        HisAlertEmailConfig other = (HisAlertEmailConfig) object;
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
