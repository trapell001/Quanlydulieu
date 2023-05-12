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
public class TblTariffPlanFreeChannelDNSDTO {

    private Integer id;
    private int tariffPlanId;
    private String channelId;
    private String description;
    private Date dateCreated;
    private Date dateModified;


}
