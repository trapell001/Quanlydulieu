package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblRptoSpecialChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TblRptoSpecialChannelDAO extends JpaRepository<TblRptoSpecialChannel,Long> {
    Optional<TblRptoSpecialChannel> findByChannelId(String channelId);
    Optional<TblRptoSpecialChannel> findByChannelName(String channelName);

}
