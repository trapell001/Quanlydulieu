package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
@Entity
@Data
@Table(name = "TBL_SNAPSHOT_TARIFF_PLAN")
@NamedQueries({
        @NamedQuery(name = "TblSnapshotTariffPlan.findAll", query = "SELECT c FROM TblSnapshotTariffPlan c")})
public class TblSnapshotTariffPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_PLAN_ID")
    private Long tariffPlanId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PLAN_NAME")
    private String planName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "PLAN_DESCRIPTION")
    private String planDescription;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LADDER_ENABLED_AMOUNT")
    private Long ladderEnabledAmount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "RETURN_PAYMENT_FEE_TYPE")
    private String returnPaymentFeeType;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_PLAN_CODE")
    private String tariffPlanCode;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_SNAPSHOTTED")
    private Date dateSnapshotted;
}
