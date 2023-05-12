/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.payload.request;

/**
 *
 * @author huynx
 */
public class SavePasswordRequest {

    private Long userId;
    private String password;

    public SavePasswordRequest() {
    }

    public SavePasswordRequest(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
