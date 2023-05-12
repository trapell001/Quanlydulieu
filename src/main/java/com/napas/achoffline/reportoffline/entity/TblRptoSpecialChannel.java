package com.napas.achoffline.reportoffline.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TBL_RPTO_SPECIAL_CHANNEL")
@Data
@NamedQueries({
        @NamedQuery(name = "TblRptoSpecialChannel.findAll", query = "SELECT t FROM TblRptoSpecialChannel t")})
public class TblRptoSpecialChannel {
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHANNEL_ID")
    private String channelId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHANNEL_NAME")
    private String channelName;

}

