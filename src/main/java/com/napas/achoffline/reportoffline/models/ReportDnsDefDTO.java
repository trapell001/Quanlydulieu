package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.ReportDisputeFunctionParameter;
import com.napas.achoffline.reportoffline.define.ReportDnsFunctionParameter;

import java.util.Map;

public class ReportDnsDefDTO {
    private String reportCode;
    private String name;
    private Map<ReportDnsFunctionParameter, Boolean> parameters;

    public ReportDnsDefDTO() {
    }

    public ReportDnsDefDTO(String reportCode, String name, Map<ReportDnsFunctionParameter, Boolean> parameters) {
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

    public Map<ReportDnsFunctionParameter, Boolean> getParameters() {
        return parameters;
    }

    public void setParameters(Map<ReportDnsFunctionParameter, Boolean> parameters) {
        this.parameters = parameters;
    }
}
