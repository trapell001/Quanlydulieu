package com.napas.achoffline.reportoffline.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.napas.achoffline.reportoffline.define.SpecialDayType;
import lombok.Data;

import java.util.Date;
import javax.validation.constraints.NotNull;

@Data
public class TblSpecialDayDTO {
    private Integer id;
    
    @NotNull(message = "Kiểu ngày đặc biệt không được để trống")
    private SpecialDayType dayType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private Date beginDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    @NotNull(message = "Ngày kết thúc không được để trống")
    private Date endDate;

    private String createBy;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    private Date createDate;
    
    private String description;
    
}
