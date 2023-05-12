package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.ReportDisputeFunctionParameter;
import com.napas.achoffline.reportoffline.define.ReportOfflineFunctionParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class ReportDisputeDef {
    private String reportCode;
    private String jasperFile;
    private String name;
    private String functionName;
    private Boolean enabled = true;
    private Map<ReportDisputeFunctionParameter, Boolean> parameters;

    public ReportDisputeDef() {
    }

    public ReportDisputeDef(String reportCode, String jasperFile, String name, String functionName, Boolean enabled, Map<ReportDisputeFunctionParameter, Boolean> parameters) {
        this.reportCode = reportCode;
        this.jasperFile = jasperFile;
        this.name = name;
        this.functionName = functionName;
        this.enabled = enabled;
        this.parameters = parameters;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getJasperFile() {
        return jasperFile;
    }

    public void setJasperFile(String jasperFile) {
        this.jasperFile = jasperFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Map<ReportDisputeFunctionParameter, Boolean> getParameters() {
        return parameters;
    }

    public void setParameters(Map<ReportDisputeFunctionParameter, Boolean> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "ReportDisputeDef{" +
                "reportCode='" + reportCode + '\'' +
                ", jasperFile='" + jasperFile + '\'' +
                ", name='" + name + '\'' +
                ", functionName='" + functionName + '\'' +
                ", enabled=" + enabled +
                ", parameters=" + parameters +
                '}';
    }
}
