/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;
import com.napas.achoffline.reportoffline.entity.TblFeeQtbs;
import com.napas.achoffline.reportoffline.models.TblFeeQtbsDTO;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author huynx
 */
public interface TblFeeQtbsDAO extends JpaRepository<TblFeeQtbs, Long> {

    public List<TblFeeQtbs> findByDateCreatedBetween(Date dateFrom, Date dateTo);

    @Query("SELECT q "
            + "FROM\n"
            + "    TblFeeQtbs  q\n"
            + "WHERE\n"
            + "    q.dateCreated BETWEEN :dateFrom AND :dateTo\n"
            + "    AND (q.sessionId >= :sessionFrom or :sessionFrom is null)\n"
            + "    AND (q.sessionId <= :sessionTo or :sessionTo is null)\n"
            + "    AND q.bic LIKE %:bank%\n"
            + "    AND (q.status = :status or :status is null)")
    public Page<TblFeeQtbs> searchPayments(Pageable paging,
            @Param("dateFrom") Date dateFrom,
            @Param("dateTo") Date dateTo,
            @Param("sessionFrom") Long sessionFrom,
            @Param("sessionTo") Long sessionTo,
            @Param("bank") String bank,
            @Param("status") QtbsPaymentStatus status);

    @Query("SELECT new com.napas.achoffline.reportoffline.models.TblFeeQtbsDTO(\n"
            + "    SUM(debitAmount),\n"
            + "    SUM(creditAmount))\n"
            + "FROM\n"
            + "    TblFeeQtbs\n"
            + "WHERE\n"
            + "        bic = :participant\n"
            + "    AND sessionId BETWEEN :sessionFrom AND :sessionTo")
    public TblFeeQtbsDTO rptFeeQtbsBySessionRange(
            @Param("participant") String participant,
            @Param("sessionFrom") Long sessionFrom,
            @Param("sessionTo") Long sessionTo
    );
}
