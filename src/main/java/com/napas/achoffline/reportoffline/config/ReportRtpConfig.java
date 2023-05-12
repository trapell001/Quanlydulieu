package com.napas.achoffline.reportoffline.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.napas.achoffline.reportoffline.models.ReportOfflineDef;
import com.napas.achoffline.reportoffline.models.ReportRtpDef;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ReportRtpConfig {

    private List<ReportRtpDef> listReportRtp;

    public List<ReportRtpDef> getListReportRtp() {
        return listReportRtp;
    }

    public void setListReportRtp(List<ReportRtpDef> listReportRtp) {
        this.listReportRtp = listReportRtp;
    }

    @PostConstruct
    public void loadConfig() throws IOException {
        String fileName = "./config/ReportRtp.yml";

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();

        listReportRtp = objectMapper.readValue(new File(fileName), new TypeReference<List<ReportRtpDef>>() {
        });
    }
}
