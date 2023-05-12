package com.napas.achoffline.reportoffline.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TBL_DNS_FEE")
@NamedQueries({
        @NamedQuery(name = "TblDnsFee.findAll", query = "SELECT t FROM TblDnsFee t")})

public class TblDnsFee {
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CODE")
    private String code;
    @Column(name = "NAME")
    private String name;

    @Column(name = "CS_TCPL")
    private String csTcpl;
    @Column(name = "CS_TCNL")
    private String csTcnl;
    @Column(name = "DV_TCPL")
    private String dvTcpl;
    @Column(name = "DV_TCNL")
    private String dvTcnl;
    @Column(name = "DNS_COMMENT")
    private String dnsComment;
    public TblDnsFee() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCsTcpl() {
        return csTcpl;
    }

    public void setCsTcpl(String csTcpl) {
        this.csTcpl = csTcpl;
    }

    public String getCsTcnl() {
        return csTcnl;
    }

    public void setCsTcnl(String csTcnl) {
        this.csTcnl = csTcnl;
    }

    public String getDvTcpl() {
        return dvTcpl;
    }

    public void setDvTcpl(String dvTcpl) {
        this.dvTcpl = dvTcpl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDvTcnl() {
        return dvTcnl;
    }

    public void setDvTcnl(String dvTcnl) {
        this.dvTcnl = dvTcnl;
    }

    public String getDnsComment() {
        return dnsComment;
    }

    public void setDnsComment(String dnsComment) {
        this.dnsComment = dnsComment;
    }

    public TblDnsFee(Long id, String code, String name, String csTcpl, String csTcnl, String dvTcpl, String dvTcnl, String dnsComment) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.csTcpl = csTcpl;
        this.csTcnl = csTcnl;
        this.dvTcpl = dvTcpl;
        this.dvTcnl = dvTcnl;
        this.dnsComment = dnsComment;
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
        if (!(object instanceof TblDnsFee)) {
            return false;
        }
        TblDnsFee other = (TblDnsFee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.authentication.models.TblDnsFee[ id=" + id + " ]";
    }

}
