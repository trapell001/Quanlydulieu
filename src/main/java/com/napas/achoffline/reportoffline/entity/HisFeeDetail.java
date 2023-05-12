package com.napas.achoffline.reportoffline.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "HIS_FEE_DETAIL")
@NamedQueries({
        @NamedQuery(name = "HisFeeDetail.findAll", query = "SELECT h FROM HisFeeDetail h")})
public class HisFeeDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIS_FEE_DETAIL")
    @SequenceGenerator(sequenceName = "SEQ_HIS_FEE_DETAIL", allocationSize = 1, name = "SEQ_HIS_FEE_DETAIL")
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "DATE_CREATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date dateCreate;

    @NotNull
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @NotNull
    @Column(name = "DATE_BEGIN")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date dateBegin;

    @NotNull
    @Column(name = "DATE_END")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date dateEnd;

    @NotNull
    @Column(name = "SLGD_TT")
    private Long slgdTT;

    @NotNull
    @Column(name = "GTGD_TT")
    private Long gtgdTT;

    @NotNull
    @Column(name = "SLGD_ALL")
    private Long slgdAll;

    @NotNull
    @Column(name = "GTGD_ALL")
    private Long gtgdAll;
}
