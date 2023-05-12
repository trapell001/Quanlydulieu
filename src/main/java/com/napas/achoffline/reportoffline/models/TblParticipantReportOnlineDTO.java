package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblParticipantReportOnlineDTO {
    private Long id;

    @NotNull(message = "Mã ngân hàng thành viên không được để trống")
    private String participantBic;

    @NotNull(message = "Url của bản tin không được để trống")
    private String endPoint;

    private boolean camt998Online;

    private boolean camt998Offline;

    private boolean camt052Online;

    private boolean camt052Offline;

    private boolean camt053Online;

    private boolean camt053Offline;
}
