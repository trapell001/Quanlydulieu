package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TBL_RPTO_LADDER")
@NamedQueries({
        @NamedQuery(name = "TblRptoLadder.findAll", query = "SELECT t FROM TblRptoLadder t")})
public class TblRptoLadder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RPTO_LADDER")
    @SequenceGenerator(sequenceName = "SEQ_RPTO_LADDER", allocationSize = 1, name = "SEQ_RPTO_LADDER")
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LADDER_NAME")
    private String ladderName;

}
