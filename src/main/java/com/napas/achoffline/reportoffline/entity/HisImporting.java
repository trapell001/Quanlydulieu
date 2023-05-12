package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "HIS_IMPORTING")
@NamedQueries({
        @NamedQuery(name = "HisImporting.findAll", query = "SELECT h FROM HisImporting h")})
public class HisImporting implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIS_IMPORTING")
    @SequenceGenerator(sequenceName = "SEQ_HIS_IMPORTING", allocationSize = 1, name = "SEQ_HIS_IMPORTING")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @NotNull
    @Column(name = "DATE_BEGIN")
    private Date dateBegin;

    @NotNull
    @Column(name = "DATE_END")
    private Date dateEnd;

    @NotNull
    @Column(name = "SLGD")
    private Long slgd;

    @NotNull
    @Column(name = "TYPE_OF_STATE")
    private String typeOfState;

    @NotNull
    @Column(name = "SERVICE")
    private String service;
}
