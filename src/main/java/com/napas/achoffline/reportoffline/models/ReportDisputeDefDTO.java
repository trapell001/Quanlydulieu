package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.ReportDisputeFunctionParameter;

import java.util.Map;

public class ReportDisputeDefDTO {
    private String reportCode;
    private String name;
    private Map<ReportDisputeFunctionParameter, Boolean> parameters;

    public ReportDisputeDefDTO() {
    }

    public ReportDisputeDefDTO(String reportCode, String name, Map<ReportDisputeFunctionParameter, Boolean> parameters) {
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

    public Map<ReportDisputeFunctionParameter, Boolean> getParameters() {
        return parameters;
    }

    public void setParameters(Map<ReportDisputeFunctionParameter, Boolean> parameters) {
        this.parameters = parameters;
    }
}
