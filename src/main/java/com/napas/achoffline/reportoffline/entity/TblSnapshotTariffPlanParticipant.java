package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "TBL_SNAPSHOT_TARIFF_PLAN_PARTICIPANT")
@NamedQueries({
        @NamedQuery(name = "TblSnapshotTariffPlanParticipant.findAll", query = "SELECT c FROM TblSnapshotTariffPlanParticipant c")})
public class TblSnapshotTariffPlanParticipant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_PLAN_PARTICIPANT_ID")
    private Long tariffPlanParticipantId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "BIC")
    private String bic;


    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIFF_PLAN_ID")
    private Long tariffPlanId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "CHANNEL_ID")
    private String channelId;


    @Basic(optional = false)
    @NotNull
    @Column(name = "CHANNEL_TYPE")
    private Long channelType;


    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_SNAPSHOTTED")
    private Date dateSnapshotted;
}
