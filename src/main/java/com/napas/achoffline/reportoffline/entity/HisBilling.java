package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "HIS_BILLING")
@NamedQueries({
        @NamedQuery(name = "HisBilling.findAll", query = "SELECT h FROM HisBilling h")})
public class HisBilling implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIS_BILLING")
    @SequenceGenerator(sequenceName = "SEQ_HIS_BILLING", allocationSize = 1, name = "SEQ_HIS_BILLING")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @NotNull
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @NotNull
    @Column(name = "DATE_BEGIN")
    private Date dateBegin;

    @NotNull
    @Column(name = "DATE_END")
    private Date dateEnd;

    @NotNull
    @Column(name = "SLGD")
    private Long slgd;
}
