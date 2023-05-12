package com.napas.achoffline.reportoffline.entity;

import javax.persistence.*;

@Entity
@Table(name = "TBL_AUTH_ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTH_ROLE_ID")
    @SequenceGenerator(sequenceName = "SEQ_AUTH_ROLE_ID", allocationSize = 1, name = "SEQ_AUTH_ROLE_ID")
    private Integer id;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String description;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
