/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.FundTransferSystem;
import com.napas.achoffline.reportoffline.define.ParticipantRole;
import com.napas.achoffline.reportoffline.define.ReportOffineTransactionType;
import com.napas.achoffline.reportoffline.type.AchServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author huynx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportOfflineParameter {

    private String title;
    private LocalDateTime dateBegin;
    private LocalDateTime dateEnd;
    private AchServiceType service;
    private Integer sessionBegin;
    private Integer sessionEnd;
    private String bic;
    private String boc;
    private String ttc;
    private ParticipantRole role;
    private ReportOffineTransactionType transType;
}
