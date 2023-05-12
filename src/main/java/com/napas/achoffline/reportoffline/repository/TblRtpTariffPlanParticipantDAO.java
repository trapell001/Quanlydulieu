/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblRtpTariffPlanParticipant;
import com.napas.achoffline.reportoffline.entity.TblTariffPlanParticipant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author huynx
 */
public interface TblRtpTariffPlanParticipantDAO extends JpaRepository<TblRtpTariffPlanParticipant, Integer> {

    List<TblRtpTariffPlanParticipant> findByTariffPlanId(int tariffPlanId);

    List<TblRtpTariffPlanParticipant> findByBic(String bic);
}
