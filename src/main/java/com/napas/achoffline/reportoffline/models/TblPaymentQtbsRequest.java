package com.napas.achoffline.reportoffline.models;

import com.napas.achoffline.reportoffline.define.QtbsPaymentType;
import lombok.Data;

import java.math.BigDecimal;

@Data

public class TblPaymentQtbsRequest {

    private QtbsPaymentType msgType;

    private String txid;

    private String description;

    private BigDecimal returnAmount;

}
