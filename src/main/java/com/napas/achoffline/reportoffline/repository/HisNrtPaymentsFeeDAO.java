/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.HisNrtPaymentsFee;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author huynx
 */
public interface HisNrtPaymentsFeeDAO extends JpaRepository<HisNrtPaymentsFee, Long> {

    public Page<HisNrtPaymentsFee> findByDebtorAgentAndSessionIdBetweenAndProcessDateBetween(
            Pageable pageable,
            String debtorAgent,
            long sessionFrom,
            long sessionTo,
            Date processDateFrom,
            Date processDateTo
    );

    public Page<HisNrtPaymentsFee> findByCreditorAgentAndSessionIdBetweenAndProcessDateBetween(
            Pageable pageable,
            String debtorAgent,
            long sessionFrom,
            long sessionTo,
            Date processDateFrom,
            Date processDateTo
    );
}
