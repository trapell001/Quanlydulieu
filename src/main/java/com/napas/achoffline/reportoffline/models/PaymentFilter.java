/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.napas.achoffline.reportoffline.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author huynx
 */
public record PaymentFilter(LocalDateTime beginDate,
        LocalDateTime endDate,
        Integer sessionId,
        Integer toSessionId,
        String mxType,
        String msgId,
        String transStatus,
        String authInfo,
        BigDecimal minAmount,
        BigDecimal maxAmount,
        String debitBank,
        String creditBank,
        String deptorName,
        String creditorName,
        String deptorAccount,
        String creditorAccount,
        String deptorAccountType,
        String creditorAccountType,
        String channelId,
        String boc,
        String ttc,
        String msgToCreditor,
        boolean covidOnly,
        String debtorSystem,
        String creditorSystem,
        String rtp) {

}
