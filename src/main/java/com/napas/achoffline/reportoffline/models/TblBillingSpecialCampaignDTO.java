package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblBillingSpecialCampaignDTO {

    @Id
    @Column(name = "CAMPAIGN_ID")
    private Long campaignId;

    @Column(name = "CAMPAIGN_NAME")
    private String campaignName;

    @Column(name = "CAMPAIGN_CODE")
    private String campaignCode;

    @Column(name = "MODIF_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date modifDate;
}
