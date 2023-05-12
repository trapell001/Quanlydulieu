package com.napas.achoffline.reportoffline.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TBL_PAYMENT_CHANNEL")
@NamedQueries({
        @NamedQuery(name = "TblPaymentChannel.findAll", query = "SELECT t FROM TblPaymentChannel t")})

public class TblPaymentChannel {
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CHANNEL_ID")
    private String channelId;
    @Column(name = "CHANNEL_NAME")
    private String channelName;
    @Column(name = "CHANNEL_DESCRIPTION")
    private String channelDescription;
    @Column(name = "CHANNEL_TYPE")
    private String channelType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public TblPaymentChannel(Long id, String channelId, String channelName, String channelDescription, String channelType) {
        this.id = id;
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.channelType = channelType;
    }

    public TblPaymentChannel() {
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
        if (!(object instanceof TblPaymentChannel)) {
            return false;
        }
        TblPaymentChannel other = (TblPaymentChannel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.models.TblPaymentChannel[ id=" + id + " ]";
    }

}
