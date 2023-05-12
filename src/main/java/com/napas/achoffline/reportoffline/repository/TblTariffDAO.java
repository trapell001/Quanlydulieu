
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblTariff;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author huynx
 */
public interface TblTariffDAO extends JpaRepository<TblTariff, Integer> {
    public Optional<TblTariff> findByTariffIdAndTariffLadderId(int tariffId, int tariffLadderId);
    public List<TblTariff> findByTariffLadderId(int tariffLadderId);
}
