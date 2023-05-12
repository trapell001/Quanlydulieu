package com.napas.achoffline.reportoffline.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TBL_USER_TOKEN")
public class TblUserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_USER_TOKEN_SEQ")
    @SequenceGenerator(sequenceName = "TBL_USER_TOKEN_SEQ", allocationSize = 1, name = "TBL_USER_TOKEN_SEQ")
    private Long id;

    @Column(name ="USERNAME")
    private String username;

    @Column(name ="TOKEN")
    private String token;

    @Column(name ="CREATEDATE_AT")
    private Date createDate;

    @Column(name ="STATUS")
    private String status;

    public TblUserToken(Long id, String username, String token, Date createDate, String status) {

        this.id = id;
        this.username = username;
        this.token = token;
        this.createDate = createDate;
        this.status = status;
    }

    public TblUserToken() {

    }

    @Override
    public String toString() {
        return "TblUserToken{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", createDate=" + createDate +
                ", status='" + status + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
