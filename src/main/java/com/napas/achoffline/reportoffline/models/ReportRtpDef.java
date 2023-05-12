package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.ReportRtpFunctionParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRtpDef {
    private String reportCode;
    private String jasperFile;
    private String name;
    private String functionName;
    private Boolean enabled = true;
    private Map<ReportRtpFunctionParameter, Boolean> parameters;
}
