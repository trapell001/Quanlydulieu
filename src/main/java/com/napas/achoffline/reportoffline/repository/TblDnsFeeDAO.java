package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblDnsFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TblDnsFeeDAO extends JpaRepository<TblDnsFee,Long> {
    Optional<TblDnsFee> findTblDnsFeeByCode(String name);

}
