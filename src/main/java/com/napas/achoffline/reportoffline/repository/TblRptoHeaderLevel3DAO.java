package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblRptoHeaderLevel3DAO extends JpaRepository<TblRptoHeaderLevel3,Integer> {

    Optional<TblRptoHeaderLevel3> findByHeaderCodeAndLevel2Id(String code, Integer level2Id);

    Optional<TblRptoHeaderLevel3> findByHeaderOrder(Integer order);

    TblRptoHeaderLevel3 findByHeaderCode(String headerCode);


    List<TblRptoHeaderLevel3>findByLevel2Id(Integer level2Id);

    Optional<TblRptoHeaderLevel3>findByHeaderOrderAndLevel2Id(Integer order, Integer level2Id);
}