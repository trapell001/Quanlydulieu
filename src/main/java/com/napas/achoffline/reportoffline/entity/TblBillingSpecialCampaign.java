package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "TBL_BILLING_SPECIAL_CAMPAIGN")
public class TblBillingSpecialCampaign {
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BILLING_SPECIAL_CAMPAIGN")
    @SequenceGenerator(sequenceName = "SEQ_BILLING_SPECIAL_CAMPAIGN", allocationSize = 1, name = "SEQ_BILLING_SPECIAL_CAMPAIGN")
    @Column(name = "CAMPAIGN_ID")
    private Long campaignId;

    @Column(name = "CAMPAIGN_NAME")
    private String campaignName;

    @Column(name = "CAMPAIGN_CODE")
    private String campaignCode;

    @Column(name = "MODIF_DATE")
    private Date modifDate;


}
