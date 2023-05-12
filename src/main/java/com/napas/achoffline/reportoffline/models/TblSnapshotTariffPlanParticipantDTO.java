package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TblSnapshotTariffPlanParticipantDTO {

    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Column(name = "TARIFF_PLAN_PARTICIPANT_ID")
    private Long tariffPlanParticipantId;

    @Column(name = "BIC")
    private String bic;

    @Column(name = "TARIFF_PLAN_ID")
    private Long tariffPlanId;

    @Column(name = "CHANNEL_ID")
    private String channelId;

    @Column(name = "CHANNEL_TYPE")
    private Long channelType;

    @Column(name = "DATE_SNAPSHOTTED")
    private Date dateSnapshotted;
}
