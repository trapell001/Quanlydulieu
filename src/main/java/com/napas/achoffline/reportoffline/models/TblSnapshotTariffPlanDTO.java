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
public class TblSnapshotTariffPlanDTO {

    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Column(name = "TARIFF_PLAN_ID")
    private Long tariffPlanId;

    @Column(name = "PLAN_NAME")
    private String planName;

    @Column(name = "PLAN_DESCRIPTION")
    private String planDescription;

    @Column(name = "LADDER_ENABLED_AMOUNT")
    private Long ladderEnabledAmount;

    @Column(name = "RETURN_PAYMENT_FEE_TYPE")
    private String returnPaymentFeeType;

    @Column(name = "TARIFF_PLAN_CODE")
    private String tariffPlanCode;

    @Column(name = "DATE_SNAPSHOTTED")
    private Date dateSnapshotted;
}
