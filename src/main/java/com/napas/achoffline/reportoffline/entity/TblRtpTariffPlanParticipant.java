/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import com.napas.achoffline.reportoffline.define.ChannelType;
import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "TBL_RTP_TARIFF_PLAN_PARTICIPANT")
@NamedQueries({
        @NamedQuery(name = "TblRtpTariffPlanParticipant.findAll", query = "SELECT t FROM TblRtpTariffPlanParticipant t")})
public class TblRtpTariffPlanParticipant implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "BIC")
    private String bic;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_PLAN_ID")
    private int tariffPlanId;
    @Column(name = "CHANNEL_ID")
    private String channelId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHANNEL_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private ChannelType channelType;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "TARIFF_PLAN_PARTICIPANT_ID")
    private Integer tariffPlanParticipantId;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "DATE_MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

    public TblRtpTariffPlanParticipant() {
    }

    public TblRtpTariffPlanParticipant(Integer tariffPlanParticipantId) {
        this.tariffPlanParticipantId = tariffPlanParticipantId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tariffPlanParticipantId != null ? tariffPlanParticipantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRtpTariffPlanParticipant)) {
            return false;
        }
        TblRtpTariffPlanParticipant other = (TblRtpTariffPlanParticipant) object;
        if ((this.tariffPlanParticipantId == null && other.tariffPlanParticipantId != null) || (this.tariffPlanParticipantId != null && !this.tariffPlanParticipantId.equals(other.tariffPlanParticipantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.models.TblRtpTariffPlanParticipant[ tariffPlanParticipantId=" + tariffPlanParticipantId + " ]";
    }

    public TblRtpTariffPlanParticipant(String bic, int tariffPlanId, String channelId, ChannelType channelType, Integer tariffPlanParticipantId, Date dateCreated, Date dateModified) {
        this.bic = bic;
        this.tariffPlanId = tariffPlanId;
        this.channelId = channelId;
        this.channelType = channelType;
        this.tariffPlanParticipantId = tariffPlanParticipantId;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }
    public TblRtpTariffPlanParticipant(String bic, int tariffPlanId, String channelId, ChannelType channelType,Date dateCreated, Date dateModified) {
        this.bic = bic;
        this.tariffPlanId = tariffPlanId;
        this.channelId = channelId;
        this.channelType = channelType;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public int getTariffPlanId() {
        return tariffPlanId;
    }

    public void setTariffPlanId(int tariffPlanId) {
        this.tariffPlanId = tariffPlanId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public Integer getTariffPlanParticipantId() {
        return tariffPlanParticipantId;
    }

    public void setTariffPlanParticipantId(Integer tariffPlanParticipantId) {
        this.tariffPlanParticipantId = tariffPlanParticipantId;
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

}
