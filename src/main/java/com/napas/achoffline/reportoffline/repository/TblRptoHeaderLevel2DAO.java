package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblRptoHeaderLevel2DAO extends JpaRepository<TblRptoHeaderLevel2, Integer> {

    Optional<TblRptoHeaderLevel2> findByHeaderCodeAndLevel1Id(String code, Integer level1Id);

    TblRptoHeaderLevel2 findByHeaderCode(String headerCode);


    Optional<TblRptoHeaderLevel2>findByHeaderOrderAndLevel1Id(Integer odder, Integer level1Id);

    List<TblRptoHeaderLevel2>findByLevel1Id(Integer leve1Id);
}