package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblBocFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TblBocFeeDAO extends JpaRepository<TblBocFee,Long> {
    Optional<TblBocFee> findTblBocFeeByCode(String name);

}
