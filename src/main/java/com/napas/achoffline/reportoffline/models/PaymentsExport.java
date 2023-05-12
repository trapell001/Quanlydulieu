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
public record PaymentsExport(
        LocalDateTime processDate,
        LocalDateTime transDate,
        Long sessionId,
        String txid,
        String instrid,
        String endtoendid,
        String type,
        String channelId,
        String transStatus,
        String authInfo,
        BigDecimal settlementAmount,
        String instructingAgent,
        String instructedAgent,
        String achResultCode,
        String partyResultCode,
        String ttc
        ) {
    
}
