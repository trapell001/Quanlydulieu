/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;

/**
 *
 * @author huynx
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblRptoHeaderLevel1DTO {

    @Id
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    // @Size(min = 1, max = 100)
    @Column(name = "HEADER_CODE")
    private String headerCode;
    @Basic(optional = false)
    @NotNull
    // @Size(min = 1, max = 500)
    @Column(name = "HEADER_NAME")
    private String headerName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HEADER_ORDER")
    private int headerOrder;
}
