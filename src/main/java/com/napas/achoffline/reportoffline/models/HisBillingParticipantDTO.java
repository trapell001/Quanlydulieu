package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HisBillingParticipantDTO {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Column(name = "PARTICIPANT_BIC")
    private String participantBic;

    @Column(name = "CHANNEL_TYPE")
    private Integer channelType;

    @Column(name = "CHANNEL_ID")
    private String channelId;

    @Column(name = "TARIFF_PLAN_ID")
    private Integer tariffPlanId;

    @NotNull
    @Column(name = "BILLING_DATE_BEGIN")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date billingDateBegin;

    @NotNull
    @Column(name = "BILLING_DATE_END")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date billingDateEnd;

    @NotNull
    @Column(name = "SLGD")
    private Long slgd;

    @Transient
    private Long processingTime;

    @Transient
    private Long session;
}
