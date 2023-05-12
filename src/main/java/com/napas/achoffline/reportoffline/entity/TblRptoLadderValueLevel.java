package com.napas.achoffline.reportoffline.entity;

import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TBL_RPTO_LADDER_VALUE_LEVEL")
@NamedQueries({
        @NamedQuery(name = "TblRptoLadderValueLevel.findAll", query = "SELECT t FROM TblRptoLadderValueLevel t")})
public class TblRptoLadderValueLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RPTO_LADDER_VALUE_LEVEL")
    @SequenceGenerator(sequenceName = "SEQ_RPTO_LADDER_VALUE_LEVEL", allocationSize = 1, name = "SEQ_RPTO_LADDER_VALUE_LEVEL")
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LADDER_ID")
    private Long ladderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALUE_RANGE_MIN")
    private Long valueRangeMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALUE_RANGE_MAX")
    private Long valueRangeMax;
}
