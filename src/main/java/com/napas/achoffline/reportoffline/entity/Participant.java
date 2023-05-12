package com.napas.achoffline.reportoffline.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "PARTICIPANTS", schema = "ACH")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARTICIPANT")
    @SequenceGenerator(sequenceName = "SEQ_PARTICIPANT", allocationSize = 1, name = "SEQ_PARTICIPANT")
    @Column(name = "PARTICIPANT_ID")
    private Long participantId;

    @NotNull
    @Column(name = "PARTICIPANT_CODE")
    private String participantCode;

    @NotNull
    @Column(name = "NAME")
    private String name;
}
