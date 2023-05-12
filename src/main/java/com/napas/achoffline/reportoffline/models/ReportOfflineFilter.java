/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.BankOperationCode;
import com.napas.achoffline.reportoffline.define.ParticipantRole;
import com.napas.achoffline.reportoffline.define.ReportOffineTransactionType;
import com.napas.achoffline.reportoffline.define.ReportOfflineParticipantType;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author huynx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportOfflineFilter {

    private String bic;
    private String sessionBegin;
    private String sessionEnd;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateBegin;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEnd;
    private BankOperationCode boc;
    private String ttc;
    private ReportOfflineParticipantType participantType;
    private String transType;
    private ParticipantRole participantRole;

}
