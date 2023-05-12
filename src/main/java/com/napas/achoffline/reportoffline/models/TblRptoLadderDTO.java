package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblRptoLadderDTO {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LADDER_NAME")
    private String ladderName;
}
