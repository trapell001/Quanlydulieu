/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @author huynx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TblAchBankParticipantsDTO {

    private Integer id;
    private String bicCode;
    private String typeId;
    private String typeGroup;
    private String createdUser;
    private Date createdDate;
    private String updatedUser;
    private Date updatedDate;
    private String tctt;
    private TblAchBankDTO achBank;

}
