package com.napas.achoffline.reportoffline.models;

/**
 *
 * @author huynx
 */
//thêm và
// sửa từ payment dashboard
public record PaymentsExportAcspNaut(
        String beginDate,
        String endDate,
        String txid,
        String msgId,
        String transStatus,
        String authInfo,
        String amount,
        String debtorAgent,
        String creditorAgent,
        String mxType,
        Long sessionId,
        String txid004,
        String transStatus004,
        String authInfo004,

        String amount004,
        String endDate004,
        String sessionId004


) {

}