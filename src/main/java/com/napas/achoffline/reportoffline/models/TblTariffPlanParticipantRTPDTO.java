package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.napas.achoffline.reportoffline.define.ChannelType;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class TblTariffPlanParticipantRTPDTO {
    private List<String> bic;
    private int tariffPlanId;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date dateModified;

    private String channelId;
    private ChannelType channelType;

    public List<String> getBic() {
        return bic;
    }

    public void setBic(List<String> bic) {
        this.bic = bic;
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

    public int getTariffPlanId() {
        return tariffPlanId;
    }

    public void setTariffPlanId(int tariffPlanId) {
        this.tariffPlanId = tariffPlanId;
    }

    public TblTariffPlanParticipantRTPDTO(List<String> bic, int tariffPlanId, Date dateCreated, Date dateModified, String channelId, ChannelType channelType) {
        this.bic = bic;
        this.tariffPlanId = tariffPlanId;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.channelId = channelId;
        this.channelType = channelType;
    }

    public TblTariffPlanParticipantRTPDTO() {
    }
}
