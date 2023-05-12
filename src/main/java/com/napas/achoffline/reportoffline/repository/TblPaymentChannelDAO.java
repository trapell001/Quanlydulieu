package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblPaymentChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TblPaymentChannelDAO extends JpaRepository<TblPaymentChannel,Long> {
    Optional<TblPaymentChannel> findByChannelId(String channelId);

}