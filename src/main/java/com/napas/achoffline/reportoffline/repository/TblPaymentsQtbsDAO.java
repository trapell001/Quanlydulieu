/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;
import com.napas.achoffline.reportoffline.define.QtbsPaymentType;
import com.napas.achoffline.reportoffline.entity.TblPaymentsQtbs;
import com.napas.achoffline.reportoffline.models.TblPaymentsQtbsDTO;
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
public interface TblPaymentsQtbsDAO extends JpaRepository<TblPaymentsQtbs, Integer> {

    public List<TblPaymentsQtbs> findByDateCreatedBetween(Date dateFrom, Date dateTo);

    @Query("SELECT new com.napas.achoffline.reportoffline.models.TblPaymentsQtbsDTO(\n"
            + "    q.dateCreated,\n"
            + "    q.sessionId,\n"
            + "    q.msgType,\n"
            + "    q.txid,\n"
            + "    q.description,\n"
            + "    q.id,\n"
            + "    q.status,\n"
            + "    q.userMaker,\n"
            + "    q.userChecker,\n"
            + "    q.returnAmount,\n"
            + "    q.dateApproved)\n"
            + "FROM\n"
            + "    TblPaymentsQtbs  q\n"
            + "WHERE\n"
            + "    q.dateCreated BETWEEN :dateFrom AND :dateTo\n"
            + "    AND q.txid LIKE %:txid%\n"
            + "    AND (q.sessionId >= :sessionFrom or :sessionFrom is null)\n"
            + "    AND (q.sessionId <= :sessionTo or :sessionTo is null)\n"
            + "    AND (q.msgType = :paymentType or :paymentType is null)\n"
            + "    AND (q.status = :status or :status is null)")
    public List<TblPaymentsQtbsDTO> searchPayments(
            @Param("dateFrom") Date dateFrom,
            @Param("dateTo") Date dateTo,
            @Param("txid") String txid,
            @Param("sessionFrom") Long sessionFrom,
            @Param("sessionTo") Long sessionTo,
            @Param("paymentType") QtbsPaymentType paymentType,
            @Param("status") QtbsPaymentStatus status);
}
