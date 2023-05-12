package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblRptoLadderValueLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblRptoLadderValueLevelDAO extends JpaRepository<TblRptoLadderValueLevel,Integer> {

    List<TblRptoLadderValueLevel>findByLadderId(Long ladderId);
}
