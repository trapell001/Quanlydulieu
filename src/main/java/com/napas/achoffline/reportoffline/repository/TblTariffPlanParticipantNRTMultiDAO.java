/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.define.ChannelType;
import com.napas.achoffline.reportoffline.entity.TblTariffPlanParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author huynx
 */
public interface TblTariffPlanParticipantNRTMultiDAO extends JpaRepository<TblTariffPlanParticipant, Integer> {
    List<TblTariffPlanParticipant> findByTariffPlanId(Integer tariffPlanId);
    List<TblTariffPlanParticipant> findByBic(String bic);
    List<TblTariffPlanParticipant> findByChannelIdAndChannelType(String channelId,ChannelType channelType );
    TblTariffPlanParticipant findByBicAndAndTariffPlanIdAndAndChannelIdAndChannelType(String bic, Integer tariffPlanId,String channelId,ChannelType channelType );
    @Query("select q from TblTariffPlanParticipant q where " +
            "(q.tariffPlanId = :tariffPlanId or :tariffPlanId is null) "
            +"AND (q.channelId like %:channelId% or :channelId is null) "
            +"AND (q.channelType = :channelType or :channelType is null) "
            +"order by q.dateModified desc "

    )

    List<TblTariffPlanParticipant> getAllByTariffPlanIdAndAndChannelIdAndChannelType(
            @Param("tariffPlanId") Integer tariffPlanId,
            @Param("channelId") String channelId,
            @Param("channelType") ChannelType channelType
    );
    @Query("select q from TblTariffPlanParticipant q where " +
            "(q.tariffPlanId = :tariffPlanId or :tariffPlanId is null) "
            +"AND (q.channelId like %:channelId% or :channelId is null) "
            +"AND (q.channelType = :channelType or :channelType is null) "
            +"AND (q.bic like %:participant% or :participant is null)"
            +"order by q.dateModified desc "
    )
    List<TblTariffPlanParticipant> getAllByBicAndTariffPlanIdAndAndChannelIdAndChannelType(
            @Param("tariffPlanId") Integer tariffPlanId,
            @Param("channelId") String channelId,
            @Param("channelType") ChannelType channelType,
            @Param("participant") String participant
    );

}
