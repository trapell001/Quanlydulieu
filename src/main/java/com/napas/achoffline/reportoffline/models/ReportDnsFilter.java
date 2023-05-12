package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.BankOperationCode;
import com.napas.achoffline.reportoffline.define.DisputeType;
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
public class ReportDnsFilter {
    private String bic;
    private String sessionBegin;
    private String sessionEnd;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateBegin;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEnd;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate responseDate;
    private BankOperationCode boc;
    private String ttc;
    private ReportOfflineParticipantType participantType;
    private String transType;
    private ParticipantRole participantRole;
    private DisputeType disputeType;
    private String status;
}
