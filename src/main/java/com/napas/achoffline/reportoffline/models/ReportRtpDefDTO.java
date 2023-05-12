package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.ReportOfflineFunctionParameter;
import com.napas.achoffline.reportoffline.define.ReportRtpFunctionParameter;

import java.util.Map;

public class ReportRtpDefDTO {

    private String reportCode;
    private String name;
    private Map<ReportRtpFunctionParameter, Boolean> parameters;

    public ReportRtpDefDTO() {
    }

    public ReportRtpDefDTO(String reportCode, String name, Map<ReportRtpFunctionParameter, Boolean> parameters) {
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

    public Map<ReportRtpFunctionParameter, Boolean> getParameters() {
        return parameters;
    }

    public void setParameters(Map<ReportRtpFunctionParameter, Boolean> parameters) {
        this.parameters = parameters;
    }
}
