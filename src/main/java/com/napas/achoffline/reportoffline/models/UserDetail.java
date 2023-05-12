/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.entity.Role;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author huynx
 */
public class UserDetail {

    private Long id;
    private String username;
    private String email;
    private String creationDate;
    private String modifDate;
    private String fullName;
    private Set<Role> roles;

    public UserDetail() {
    }

    public UserDetail(Long id, String username, String email, String creationDate, String modifDate, Set<Role> roles, String fullName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.creationDate = creationDate;
        this.modifDate = modifDate;
        this.roles = roles;
        this.fullName = fullName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModifDate() {
        return modifDate;
    }

    public void setModifDate(String modifDate) {
        this.modifDate = modifDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
