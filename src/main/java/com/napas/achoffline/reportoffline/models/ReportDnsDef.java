package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.ReportDnsFunctionParameter;
import com.napas.achoffline.reportoffline.define.ReportOfflineFunctionParameter;
import lombok.Data;

import java.util.Map;

@Data
public class ReportDnsDef {
    private String reportCode;
    private String jasperFile;
    private String name;
    private String functionName;
    private Boolean enabled = true;
    private Map<ReportDnsFunctionParameter, Boolean> parameters;
}
