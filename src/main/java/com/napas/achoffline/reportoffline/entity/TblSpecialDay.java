package com.napas.achoffline.reportoffline.entity;

import com.napas.achoffline.reportoffline.define.SpecialDayType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "TBL_SPECIAL_DAY")
@NamedQueries({
    @NamedQuery(name = "TblSpecialDay.findAll", query = "SELECT t FROM TblSpecialDay t")})
public class TblSpecialDay implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBL_SPECIAL_DAY")
    @SequenceGenerator(sequenceName = "SEQ_TBL_SPECIAL_DAY", allocationSize = 1, name = "SEQ_TBL_SPECIAL_DAY")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DAY_TYPE")
    @Enumerated(EnumType.STRING)
    private SpecialDayType dayType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BEGIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Size(max = 20)
    @Column(name = "CREATE_BY")
    private String createBy;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
    @Size(max = 1000)
    @Column(name = "DESCRIPTION")
    private String description;

    public TblSpecialDay() {
    }

    public TblSpecialDay(Integer id) {
        this.id = id;
    }

    public TblSpecialDay(Integer id, SpecialDayType dayType, Date beginDate, Date endDate, String description) {
        this.id = id;
        this.dayType = dayType;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SpecialDayType getDayType() {
        return dayType;
    }

    public void setDayType(SpecialDayType dayType) {
        this.dayType = dayType;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof TblSpecialDay)) {
            return false;
        }
        TblSpecialDay other = (TblSpecialDay) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.napas.achoffline.reportoffline.entity.TblSpecialDay[ id=" + id + " ]";
    }
}
