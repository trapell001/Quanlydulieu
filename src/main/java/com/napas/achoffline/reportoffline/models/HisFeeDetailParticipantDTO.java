package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HisFeeDetailParticipantDTO {

    private Long processingTime;

    private Long session;

    private String type;
}
