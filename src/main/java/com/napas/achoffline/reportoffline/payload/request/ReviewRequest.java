/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.payload.request;

import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;

/**
 *
 * @author huynx
 */
public class ReviewRequest {

    private QtbsPaymentStatus status;

    public QtbsPaymentStatus getStatus() {
        return status;
    }

    public void setStatus(QtbsPaymentStatus status) {
        this.status = status;
    }
}
