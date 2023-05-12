package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblSnapshotTariffLadderDTO {

    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Column(name = "TARIFF_LADDER_ID")
    private Long tariffLadderId;

    @Column(name = "TARIFF_PLAN_ID")
    private Long tariffPlanId;

    @Column(name = "MIN_NUM_TRANS")
    private Long minNumTrans;

    @Column(name = "MAX_NUM_TRANS")
    private Long maxNumTrans;

    @Column(name = "TARIFF_LADDER_DESCRIPTION")
    private String tariffLadderDescription;

    @Column(name = "DATE_SNAPSHOTTED")
    private Date dateSnapshotted;
}
