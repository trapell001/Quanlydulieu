package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.define.AchInputMessages;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HuyNX
 */
@Repository
public interface AchInputMessagesDAO extends JpaRepository<AchInputMessages, Long> {
    public List<AchInputMessages> findByMxMsgIDOrMxOrigMsgID(Sort sort, String msgId, String origMsgId);
}
