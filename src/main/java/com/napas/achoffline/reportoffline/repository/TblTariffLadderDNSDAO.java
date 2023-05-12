/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblTariffLadderDNS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author huynx
 */
public interface TblTariffLadderDNSDAO extends JpaRepository<TblTariffLadderDNS, Integer> {

    List<TblTariffLadderDNS> findByTariffPlanId(int tariffPlanId);
}
