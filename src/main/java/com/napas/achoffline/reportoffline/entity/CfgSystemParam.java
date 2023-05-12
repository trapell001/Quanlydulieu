/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author HuyNX
 */
@Entity
@Table(name = "CFG_SYSTEM_PARAM")
@NamedQueries({
    @NamedQuery(name = "CfgSystemParam.findAll", query = "SELECT c FROM CfgSystemParam c")})
public class CfgSystemParam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;

    @Size(max = 100)
    @Column(name = "PARAM_GROUP")
    private String paramGroup;

    @Size(max = 1000)
    @Column(name = "NAME")
    private String name;

    @Size(max = 1000)
    @Column(name = "VAL")
    private String val;

    @Size(max = 20)
    @Column(name = "VAL_INPUT_TYPE")
    private String inputType;

    @Size(max = 100)
    @Column(name = "PARAM_TITLE")
    private String title;

    @Column(name = "VAL_MIN")
    private Integer valMin;

    @Size(max = 4000)
    @Column(name = "VAL_LIST")
    private String valList;

    @Column(name = "VAL_MAX")
    private Integer valMax;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Column(name = "MODIF_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifDate;
    @Size(max = 1000)
    @Column(name = "DESCRIPTION")
    private String description;

    @Size(max = 100)
    @Column(name = "VAL_UNIT")
    private String valUnit;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "PARAM_ORDER")
    private Integer paramOrder;

    public CfgSystemParam() {
    }

    public CfgSystemParam(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CfgSystemParam{" +
                "id=" + id +
                ", paramGroup='" + paramGroup + '\'' +
                ", name='" + name + '\'' +
                ", val='" + val + '\'' +
                ", inputType='" + inputType + '\'' +
                ", title='" + title + '\'' +
                ", valMin=" + valMin +
                ", valList='" + valList + '\'' +
                ", valMax=" + valMax +
                ", modifDate=" + modifDate +
                ", description='" + description + '\'' +
                ", valUnit='" + valUnit + '\'' +
                ", enabled=" + enabled +
                ", paramOrder=" + paramOrder +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Date getModifDate() {
        return modifDate;
    }

    public void setModifDate(Date modifDate) {
        this.modifDate = modifDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParamGroup() {
        return paramGroup;
    }

    public void setParamGroup(String paramGroup) {
        this.paramGroup = paramGroup;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getValMin() {
        return valMin;
    }

    public void setValMin(Integer valMin) {
        this.valMin = valMin;
    }

    public String getValList() {
        return valList;
    }

    public void setValList(String valList) {
        this.valList = valList;
    }

    public Integer getValMax() {
        return valMax;
    }

    public void setValMax(Integer valMax) {
        this.valMax = valMax;
    }

    public String getValUnit() {
        return valUnit;
    }

    public void setValUnit(String valUnit) {
        this.valUnit = valUnit;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getParamOrder() {
        return paramOrder;
    }

    public void setParamOrder(Integer paramOrder) {
        this.paramOrder = paramOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgSystemParam)) {
            return false;
        }
        CfgSystemParam other = (CfgSystemParam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
