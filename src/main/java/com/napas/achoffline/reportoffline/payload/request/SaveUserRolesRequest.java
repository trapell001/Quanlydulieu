/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.payload.request;

import com.napas.achoffline.reportoffline.entity.Role;
import java.util.List;
import java.util.Set;

/**
 *
 * @author huynx
 */
public class SaveUserRolesRequest {

    private Long id;
    private Set<Integer> roles;

    public SaveUserRolesRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Integer> getRoles() {
        return roles;
    }

    public void setRoles(Set<Integer> roles) {
        this.roles = roles;
    }

    public SaveUserRolesRequest(Long id, Set<Integer> roles) {
        this.id = id;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SaveUserRolesRequest{" + "id=" + id + ", roles=" + roles + '}';
    }

}
