package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "HIS_BILLING_PARTICIPANT")
@NamedQueries({
        @NamedQuery(name = "HisBillingParticipant.findAll", query = "SELECT h FROM HisBillingParticipant h")})
public class HisBillingParticipant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIS_BILLING_PARTICIPANT")
    @SequenceGenerator(sequenceName = "SEQ_HIS_BILLING_PARTICIPANT", allocationSize = 1, name = "SEQ_HIS_BILLING_PARTICIPANT")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @NotNull
    @Column(name = "PARTICIPANT_BIC")
    private String participantBic;

    @NotNull
    @Column(name = "CHANNEL_TYPE")
    private Integer channelType;

    @Column(name = "CHANNEL_ID")
    private String channelId;

    @NotNull
    @Column(name = "TARIFF_PLAN_ID")
    private Integer tariffPlanId;

    @NotNull
    @Column(name = "BILLING_DATE_BEGIN")
    private Date billingDateBegin;

    @NotNull
    @Column(name = "BILLING_DATE_END")
    private Date billingDateEnd;

    @NotNull
    @Column(name = "SLGD")
    private Long slgd;


    @Column(name = "DATE_CREATED")
    private Date dateCreated;


}
