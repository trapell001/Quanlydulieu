/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author huynx
 */
@Entity
@Table(name = "HIS_API_ACCESS")
@NamedQueries({
    @NamedQuery(name = "HisApiAccess.findAll", query = "SELECT h FROM HisApiAccess h")})
public class HisApiAccess implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_API_ACCESS")
    @SequenceGenerator(sequenceName = "SEQ_API_ACCESS", allocationSize = 1, name = "SEQ_API_ACCESS")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE_ACCESS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAccess;

    @Size(max = 50)
    @Column(name = "USERNAME")
    private String username;

    @Size(max = 255)
    @Column(name = "REMOTE_ADDR")
    private String remoteAddr;

    @Size(max = 20)
    @Column(name = "API_METHOD")
    private String apiMethod;
    @Size(max = 255)
    @Column(name = "API_URI")
    private String apiUri;
    @Size(max = 1000)
    @Column(name = "API_PARAM")
    private String apiParam;

    @Size(max = 1000)
    @Column(name = "ADD_INFO")
    private String addInfo;

    public HisApiAccess() {
    }

    public HisApiAccess(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateAccess() {
        return dateAccess;
    }

    public void setDateAccess(Date dateAccess) {
        this.dateAccess = dateAccess;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getApiUri() {
        return apiUri;
    }

    public void setApiUri(String apiUri) {
        this.apiUri = apiUri;
    }

    public String getApiParam() {
        return apiParam;
    }

    public void setApiParam(String apiParam) {
        this.apiParam = apiParam;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
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
        if (!(object instanceof HisApiAccess)) {
            return false;
        }
        HisApiAccess other = (HisApiAccess) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.entity.HisApiAccess[ id=" + id + " ]";
    }

}
