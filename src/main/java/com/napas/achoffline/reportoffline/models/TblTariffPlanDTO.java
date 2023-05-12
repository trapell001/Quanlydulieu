/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.napas.achoffline.reportoffline.define.ReturnPaymentFeeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblTariffPlanDTO {

    private Integer tariffPlanId;

    @NotNull
    @Size(min = 1, max = 100, message = "Tên biểu phí phải có độ dài từ 1 đến 100")
    private String planName;

    @Size(min = 0, max = 1000)
    private String planDescription;

    @NotNull
    private Long ladderEnabledAmount;
    private ReturnPaymentFeeType returnPaymentFeeType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date dateCreate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date dateModified;

    private String tariffPlanCode;

}
