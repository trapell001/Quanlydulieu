package com.napas.achoffline.reportoffline.models;

import lombok.Data;

import java.util.List;

@Data
public class HisReportOnlineResendDTO {
    private List<Long> Id;

    @Override
    public String toString() {
        return "" + Id;
    }
}
