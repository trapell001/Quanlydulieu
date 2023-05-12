package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HisReportOnlinePerformaneDTO {
    private Double processingTime;
    private Long slgd;
    private Long session;

    public HisReportOnlinePerformaneDTO(Double processingTime,Long slgd, Long session) {
        this.processingTime = processingTime;
        this.slgd=slgd;
        this.session = session;
    }
}
