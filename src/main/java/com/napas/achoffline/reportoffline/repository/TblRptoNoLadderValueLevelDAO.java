package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblRptoNoLadderValueLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TblRptoNoLadderValueLevelDAO extends JpaRepository<TblRptoNoLadderValueLevel,Integer> {
}
