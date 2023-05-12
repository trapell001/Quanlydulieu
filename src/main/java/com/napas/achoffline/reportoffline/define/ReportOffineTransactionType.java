/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.napas.achoffline.reportoffline.define;

/**
 *
 * @author huynx
 */
public enum ReportOffineTransactionType {
    PAYMENT("HD_LV_1_PAYMENT"),
    RETURN("HD_LV_1_RETURN"),
    ADDED_PAYMENT("HD_LV_1_QTBS_PAYMENT"),
    ADDED_RETURN("HD_LV_1_QTBS_RETURN"),
    ADD_VOID("HD_LV_1_QTBS_VOID");

    private String description;

    private ReportOffineTransactionType() {
    }

    private ReportOffineTransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
