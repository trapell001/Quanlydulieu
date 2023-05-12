package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.BankOperationCode;
import com.napas.achoffline.reportoffline.define.ParticipantRole;
import com.napas.achoffline.reportoffline.define.ReportOfflineParticipantType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRtpFilter {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateBegin;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEnd;
    private String sessionBegin;
    private String sessionEnd;
    private String bic;
    private ReportOfflineParticipantType participantType;
    private ParticipantRole participantRole;
    private String service;
    private String status;
}
