/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblRtpTariffPlan;
import com.napas.achoffline.reportoffline.entity.TblTariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author huynx
 */
public interface TblRtpTariffPlanDAO extends JpaRepository<TblRtpTariffPlan, Integer> {

    @Query(value = "select * from TBL_RTP_TARIFF_PLAN  order by TARIFF_PLAN_ID desc", nativeQuery = true)
    List<TblRtpTariffPlan>searchAll();

}
