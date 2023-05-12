/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblFeeQtbsDTO{

    private Long id;
    private String bic;
    private Long sessionId;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private QtbsPaymentStatus status;
    private String userMaker;
    private String userChecker;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date dateApproved;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date dateCreated;
    private String description;

    public TblFeeQtbsDTO(BigDecimal debitAmount, BigDecimal creditAmount) {
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

}
