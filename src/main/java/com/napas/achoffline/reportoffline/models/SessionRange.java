/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author huynx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionRange {

    private TblSessions beginSession;
    private TblSessions endSession;
    private List<TblSessions> listSessions;

    //Tháng tính toán của session
    private LocalDate sessionMonth;


}
