/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblBillingSpecialBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author huynx
 */
@Repository
public interface TblBillingSpecialBankAccountDAO extends JpaRepository<TblBillingSpecialBankAccount, Integer> {

}
