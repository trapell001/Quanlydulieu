/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.ReportOfflineFunctionParameter;
import java.util.Map;

/**
 *
 * @author huynx
 */
public class ReportOfflineDefDTO {

    private String reportCode;
    private String name;
    private Map<ReportOfflineFunctionParameter, Boolean> parameters;

    public ReportOfflineDefDTO() {
    }

    public ReportOfflineDefDTO(String reportCode, String name, Map<ReportOfflineFunctionParameter, Boolean> parameters) {
        this.reportCode = reportCode;
        this.name = name;
        this.parameters = parameters;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<ReportOfflineFunctionParameter, Boolean> getParameters() {
        return parameters;
    }

    public void setParameters(Map<ReportOfflineFunctionParameter, Boolean> parameters) {
        this.parameters = parameters;
    }

}
