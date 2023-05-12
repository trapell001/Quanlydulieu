/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.AchSessions;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HuyNX
 */
@Repository
public interface AchSessionsDAO extends JpaRepository<AchSessions, Long> {

    public List<AchSessions> findBySessionTypeId(Sort sort, Long type);
    public List<AchSessions> findBySessionTypeIdAndStartTimeAfter(Sort sort, Long type, Date beginCheckDate);
}
