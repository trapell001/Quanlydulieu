package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel2;
import com.napas.achoffline.reportoffline.entity.TblRptoLadder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TblRptoLadderDAO extends JpaRepository<TblRptoLadder, Integer> {

    Optional<TblRptoLadder> findByLadderName(String name);
}
