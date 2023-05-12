package com.napas.achoffline.reportoffline.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PaymentHistoryDTO {
    private String txid;
    private String msgId;
    private String mxType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date processDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date modifDate;
    private String instructingAgent;
    private String instructedAgent;
    private BigDecimal amount;
    private String channel;
    private String transStatus;
    private String mxAuthInfo;
}
