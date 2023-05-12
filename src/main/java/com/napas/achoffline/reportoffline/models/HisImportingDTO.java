package com.napas.achoffline.reportoffline.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HisImportingDTO {
    @Id
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "DATE_CREATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date dateCreate;

    @NotNull
    @Column(name = "DATE_BEGIN")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date dateBegin;

    @NotNull
    @Column(name = "DATE_END")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date dateEnd;

    @NotNull
    @Column(name = "SLGD")
    private Long slgd;

    @NotNull
    @Column(name = "TYPE_OF_STATE")
    private String typeOfState;

    @NotNull
    @Column(name = "SERVICE")
    private String service;

    @Transient
    private Long processingTime;

    @Transient
    private Double processingTimeRatio;
}