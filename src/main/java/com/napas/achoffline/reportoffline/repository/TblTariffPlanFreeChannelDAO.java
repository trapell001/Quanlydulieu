/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblTariffPlanFreeChannel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author huynx
 */
public interface TblTariffPlanFreeChannelDAO extends JpaRepository<TblTariffPlanFreeChannel, Integer> {

    public List<TblTariffPlanFreeChannel> findByTariffPlanId(int tariffPlanId);
}
