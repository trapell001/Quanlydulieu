/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblTariffWithoutLadder;
import java.util.List;

import com.napas.achoffline.reportoffline.entity.TblTariffWithoutLadderDNS;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author huynx
 */
public interface TblTariffWithoutLadderDNSDAO extends JpaRepository<TblTariffWithoutLadderDNS, Integer> {

    public List<TblTariffWithoutLadderDNS> findByTariffPlanId(int tariffPlanId);
}
