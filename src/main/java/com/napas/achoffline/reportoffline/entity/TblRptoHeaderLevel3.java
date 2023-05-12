package com.napas.achoffline.reportoffline.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TBL_RPTO_HEADER_LEVEL_3")
@NamedQueries({
        @NamedQuery(name = "TblRptoHeaderLevel3.findAll", query = "SELECT t FROM TblRptoHeaderLevel3 t")})
public class TblRptoHeaderLevel3 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RPTO_HEADER_LEVEL_3")
    @SequenceGenerator(sequenceName = "SEQ_RPTO_HEADER_LEVEL_3", allocationSize = 1, name = "SEQ_RPTO_HEADER_LEVEL_3")
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_LEVEL_2")
    private Integer level2Id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "HEADER_CODE")
    private String headerCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "HEADER_NAME")
    private String headerName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HEADER_ORDER")
    private int headerOrder;

}
