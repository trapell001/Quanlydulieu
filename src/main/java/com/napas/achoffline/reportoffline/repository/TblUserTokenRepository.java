package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblUserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TblUserTokenRepository  extends JpaRepository<TblUserToken, Long> {
    TblUserToken deleteByToken(String token);

    TblUserToken findByToken( String token);
}
