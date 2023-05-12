/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.define.ChannelType;
import com.napas.achoffline.reportoffline.entity.HisImporting;
import com.napas.achoffline.reportoffline.entity.TblTariffPlanParticipant;

import java.util.Date;
import java.util.List;

import com.napas.achoffline.reportoffline.entity.TblTariffPlanParticipantDNS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author huynx
 */
public interface TblTariffPlanParticipantDNSDAO extends JpaRepository<TblTariffPlanParticipantDNS, Integer> {
    List<TblTariffPlanParticipantDNS> findByTariffPlanId(Integer tariffPlanId);
    List<TblTariffPlanParticipantDNS> findByBic(String bic);
    List<TblTariffPlanParticipantDNS> findByChannelIdAndChannelType(String channelId,ChannelType channelType );
    TblTariffPlanParticipantDNS findByBicAndAndTariffPlanIdAndAndChannelIdAndChannelType(String bic, Integer tariffPlanId,String channelId,ChannelType channelType );
    @Query("select q from TblTariffPlanParticipantDNS q where " +
            "(q.tariffPlanId = :tariffPlanId or :tariffPlanId is null) "
            +"AND (q.channelId like %:channelId% or :channelId is null) "
            +"AND (q.channelType = :channelType or :channelType is null) "
            +"order by q.dateModified desc "

    )

    List<TblTariffPlanParticipantDNS> getAllByTariffPlanIdAndAndChannelIdAndChannelType(
            @Param("tariffPlanId") Integer tariffPlanId,
            @Param("channelId") String channelId,
            @Param("channelType") ChannelType channelType
    );
    @Query("select q from TblTariffPlanParticipantDNS q where " +
            "(q.tariffPlanId = :tariffPlanId or :tariffPlanId is null) "
            +"AND (q.channelId like %:channelId% or :channelId is null) "
            +"AND (q.channelType = :channelType or :channelType is null) "
            +"AND (q.bic like %:participant% or :participant is null)"
            +"order by q.dateModified desc "
    )

    List<TblTariffPlanParticipantDNS> getAllByBicAndTariffPlanIdAndAndChannelIdAndChannelType(
            @Param("tariffPlanId") Integer tariffPlanId,
            @Param("channelId") String channelId,
            @Param("channelType") ChannelType channelType,
            @Param("participant") String participant
    );

}
