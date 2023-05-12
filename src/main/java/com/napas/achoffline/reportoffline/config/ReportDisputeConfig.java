package com.napas.achoffline.reportoffline.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.napas.achoffline.reportoffline.models.ReportDisputeDef;
import com.napas.achoffline.reportoffline.models.ReportOfflineDef;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ReportDisputeConfig {
    private List<ReportDisputeDef> listReportDispute;

    public List<ReportDisputeDef> getListReportDispute() {
        return listReportDispute;
    }

    public void setListReportDispute(List<ReportDisputeDef> listReportDispute) {
        this.listReportDispute = listReportDispute;
    }

    @PostConstruct
    public void loadConfig() throws IOException {
        String fileName = "./config/ReportDispute.yml";

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();

        listReportDispute = objectMapper.readValue(new File(fileName), new TypeReference<List<ReportDisputeDef>>() {
        });
    }
}
