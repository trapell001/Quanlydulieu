/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.HisApiAccess;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author huynx
 */
@Repository
public interface HisApiAccessDAO extends JpaRepository<HisApiAccess, Long> {

    @Query(value = "SELECT * FROM HIS_API_ACCESS WHERE DATE_ACCESS BETWEEN :startDate AND :endDate "
            + " and (:username is null or USERNAME like %:username%)"
            + " and (:method is null or API_METHOD = :method)",
            countQuery = "SELECT count(1) FROM HIS_API_ACCESS WHERE DATE_ACCESS BETWEEN :startDate AND :endDate "
            + " and (:username is null or USERNAME like %:username%)"
            + " and (:method is null or API_METHOD = :method)",
            nativeQuery = true)
    public Page<HisApiAccess> find(
            Pageable sort,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("username") String username,
            @Param("method") String method);
}
