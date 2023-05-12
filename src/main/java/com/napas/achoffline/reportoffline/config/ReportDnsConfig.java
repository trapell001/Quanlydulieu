package com.napas.achoffline.reportoffline.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.napas.achoffline.reportoffline.models.ReportDnsDef;
import com.napas.achoffline.reportoffline.models.ReportOfflineDef;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ReportDnsConfig {
    private List<ReportDnsDef> listReportDns;

    public List<ReportDnsDef> getListReportDns() {
        return listReportDns;
    }

    public void setListReportDns(List<ReportDnsDef> listReportDns) {
        this.listReportDns = listReportDns;
    }

    @PostConstruct
    public void loadConfig() throws IOException {
        String fileName = "./config/ReportDns.yml";

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();

        listReportDns = objectMapper.readValue(new File(fileName), new TypeReference<List<ReportDnsDef>>() {
        });
    }
}
