package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TBL_PARTICIPANT_REPORT_ONLINE")
@NamedQueries({
        @NamedQuery(name = "TblParticipantReportOnline.findAll", query = "SELECT t FROM TblAchBank t")})
public class TblParticipantReportOnline  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARTICIPANT_REPORT_ONLINE")
    @SequenceGenerator(sequenceName = "SEQ_PARTICIPANT_REPORT_ONLINE", allocationSize = 1, name = "SEQ_PARTICIPANT_REPORT_ONLINE")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PARTICIPANT_BIC")
    private String participantBic;

    @Column(name = "END_POINT")
    private String endPoint;

    @Column(name = "CAMT998_ONLINE")
    private boolean camt998Online;

    @Column(name = "CAMT998_OFFLINE")
    private boolean camt998Offline;

    @Column(name = "CAMT052_ONLINE")
    private boolean camt052Online;

    @Column(name = "CAMT052_OFFLINE")
    private boolean camt052Offline;

    @Column(name = "CAMT053_ONLINE")
    private boolean camt053Online;

    @Column(name = "CAMT053_OFFLINE")
    private boolean camt053Offline;

}
