package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HIS_RPT_CAMT_DETAIL")
public class RptCamtDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "A_MESSAGE_ID")
    private Long aMessageId;


    @Column(name = "MX_TXID")
    private String mxTxid;

    @Column(name = "A_MSG_TYPE")
    private String aMsgType;


    @Column(name = "MX_PGNB")
    private Long mxPageNumber;


    @Column(name = "MX_SESSION")
    private Long mxSession;


    @Column(name = "MX_LASTPGIND")
    private String mxLastPgind;


    @Column(name = "MX_TXID_REFERENCE")
    private String mxTxidReference;

    @Column(name = "MX_ACCOUNT")
    private String mxAccount;

    @Column(name = "A_MSG_RECEIVER")
    private String aMsgReceiver;

    @Column(name = "A_OPERDAY_ID")
    private Long aOperdayId;

    @Column(name = "MX_PARTICIPANT_AGENT")
    private String mxParticipantAgent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    private Date createDate;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "A_MODIF_DATE")
    private Date modifDate;
}
