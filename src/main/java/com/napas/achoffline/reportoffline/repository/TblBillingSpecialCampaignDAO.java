package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblBillingSpecialCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblBillingSpecialCampaignDAO extends JpaRepository<TblBillingSpecialCampaign,Long> {

    @Query(value = "select * from TBL_BILLING_SPECIAL_CAMPAIGN order by CAMPAIGN_ID desc", nativeQuery = true)
    List<TblBillingSpecialCampaign> searchAll();

}
