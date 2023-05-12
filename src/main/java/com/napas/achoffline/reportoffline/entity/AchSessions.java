/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author HuyNX
 */
@Entity
@Table(name = "SESSIONS", schema = "ACH")
@NamedQueries({
    @NamedQuery(name = "AchSessions.findAll", query = "SELECT a FROM AchSessions a")})
public class AchSessions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SESSION_TYPE_ID")
    private long sessionTypeId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "FINISH_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishTime;

    @Basic(optional = false)
    @NotNull
    @Column(name = "START_OPERDAY_ID")
    private long startOperdayId;
    @Column(name = "FINISH_OPERDAY_ID")
    private Long finishOperdayId;
    @Column(name = "M_CHANGE_ID")
    private Long mChangeId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "M_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mDate;

    public AchSessions() {
    }

    public AchSessions(Long id) {
        this.id = id;
    }

    public AchSessions(Long id, long sessionTypeId, Date startTime, long startOperdayId) {
        this.id = id;
        this.sessionTypeId = sessionTypeId;
        this.startTime = startTime;
        this.startOperdayId = startOperdayId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSessionTypeId() {
        return sessionTypeId;
    }

    public void setSessionTypeId(long sessionTypeId) {
        this.sessionTypeId = sessionTypeId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public long getStartOperdayId() {
        return startOperdayId;
    }

    public void setStartOperdayId(long startOperdayId) {
        this.startOperdayId = startOperdayId;
    }

    public Long getFinishOperdayId() {
        return finishOperdayId;
    }

    public void setFinishOperdayId(Long finishOperdayId) {
        this.finishOperdayId = finishOperdayId;
    }

    public Long getMChangeId() {
        return mChangeId;
    }

    public void setMChangeId(Long mChangeId) {
        this.mChangeId = mChangeId;
    }

    public Date getMDate() {
        return mDate;
    }

    public void setMDate(Date mDate) {
        this.mDate = mDate;
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
        if (!(object instanceof AchSessions)) {
            return false;
        }
        AchSessions other = (AchSessions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.models.AchSessions[ id=" + id + " ]";
    }

}
