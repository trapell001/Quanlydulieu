/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.ReportOfflineCode;
import com.napas.achoffline.reportoffline.define.ReportOfflineFunctionParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 *
 * @author huynx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportOfflineDef {

    private String reportCode;
    private String jasperFile;
    private String name;
    private String functionName;
    private Boolean enabled = true;
    private Map<ReportOfflineFunctionParameter, Boolean> parameters;

}
