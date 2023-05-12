/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblSpecialDay;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author huynx
 */
@Repository
public interface DAOTblSpecialDay extends JpaRepository<TblSpecialDay, Integer> {
    public List<TblSpecialDay> findAllByBeginDateAfter(@Param("checkBeginDate") Date checkBeginDate);

    @Query("select t from TblSpecialDay t where :date between t.beginDate and t.endDate")
    TblSpecialDay findByBetween(@Param("date") Date date);
}
