package com.napas.achoffline.reportoffline.repository;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.napas.achoffline.reportoffline.entity.AchOutputMessages;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HuyNX
 */
@Repository
public interface AchOutputMessagesDAO extends JpaRepository<AchOutputMessages, Long> {
    public List<AchOutputMessages> findByInputMessageIDIn(Sort sort, List<Long> listInputMsgId);

    @Query("""
        select a from AchOutputMessages a where 
        a.inputMessageID in :listInputMsgId 
        or a.block4 like %:reference%
    """)
    public List<AchOutputMessages> findByInputMessageDispute(Sort sort,
                                                       @Param("listInputMsgId") List<Long> listInputMsgId,
                                                       @Param("reference") String reference);
}

