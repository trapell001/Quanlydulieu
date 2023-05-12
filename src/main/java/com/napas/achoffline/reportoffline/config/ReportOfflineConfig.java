/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.napas.achoffline.reportoffline.models.ReportOfflineDef;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 *
 * @author huynx
 */
@Component
public class ReportOfflineConfig {

    private List<ReportOfflineDef> listReportOffline;

    public List<ReportOfflineDef> getListReportOffline() {
        return listReportOffline;
    }

    public void setListReportOffline(List<ReportOfflineDef> listReportOffline) {
        this.listReportOffline = listReportOffline;
    }

    @PostConstruct
    public void loadConfig() throws IOException {
        String fileName = "./config/ReportOffline.yml";

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();

        listReportOffline = objectMapper.readValue(new File(fileName), new TypeReference<List<ReportOfflineDef>>() {
        });
    }
}
