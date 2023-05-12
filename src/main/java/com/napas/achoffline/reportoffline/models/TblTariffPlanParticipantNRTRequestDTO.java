package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.napas.achoffline.reportoffline.define.ChannelType;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class TblTariffPlanParticipantNRTRequestDTO {
    private List<String> bic;
    private int oldTariffPlanId;
    private int newTariffPlanId;

    private String channelId;
    private ChannelType channelType;

    public List<String> getBic() {
        return bic;
    }

    public void setBic(List<String> bic) {
        this.bic = bic;
    }

    public int getOldTariffPlanId() {
        return oldTariffPlanId;
    }

    public void setTariffPlanIdOld(int oldTariffPlanId) {
        this.oldTariffPlanId = oldTariffPlanId;
    }

    public int getNewTariffPlanId() {
        return newTariffPlanId;
    }

    public void setNewTariffPlanId(int newTariffPlanId) {
        this.newTariffPlanId = newTariffPlanId;
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

    public TblTariffPlanParticipantNRTRequestDTO(List<String> bic, int tariffPlanId, Date dateCreated, Date dateModified, String channelId, ChannelType channelType) {
        this.bic = bic;
        this.oldTariffPlanId = tariffPlanId;
        this.channelId = channelId;
        this.channelType = channelType;
    }

    public TblTariffPlanParticipantNRTRequestDTO() {
    }
}
