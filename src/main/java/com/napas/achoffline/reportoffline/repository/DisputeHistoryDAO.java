package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.DisputeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisputeHistoryDAO extends JpaRepository<DisputeHistory, Long> {

    List<DisputeHistory> findByDispId(Long dispId);
}
