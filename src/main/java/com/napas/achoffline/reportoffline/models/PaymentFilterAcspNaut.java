package com.napas.achoffline.reportoffline.models;

import java.time.LocalDateTime;

/**
 *
 * @author huynx
 */
//thêm và
// sửa từ payment dashboard
public record PaymentFilterAcspNaut(LocalDateTime beginDate,
                                    LocalDateTime endDate,
                                    String mxType,
                                    String participant,
                                    String haveReturn
) {

}