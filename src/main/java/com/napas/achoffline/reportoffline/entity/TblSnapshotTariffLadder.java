package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "TBL_SNAPSHOT_TARIFF_LADDER")
@NamedQueries({
        @NamedQuery(name = "TblSnapshotTariffLadder.findAll", query = "SELECT c FROM TblSnapshotTariffLadder c")})
public class TblSnapshotTariffLadder implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_LADDER_ID")
    private Long tariffLadderId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_PLAN_ID")
    private Long tariffPlanId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MIN_NUM_TRANS")
    private Long minNumTrans;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_NUM_TRANS")
    private Long maxNumTrans;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_LADDER_DESCRIPTION")
    private String tariffLadderDescription;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_SNAPSHOTTED")
    private Date dateSnapshotted;
}
