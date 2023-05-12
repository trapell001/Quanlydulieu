/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.napas.achoffline.reportoffline.repository;

import java.util.List;
import java.util.Optional;

import com.napas.achoffline.reportoffline.entity.TblRptoHeaderLevel1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author huynx
 */
@Repository
public interface TblRptoHeaderLevel1DAO extends JpaRepository<TblRptoHeaderLevel1, Integer> {

    Optional<TblRptoHeaderLevel1> findByHeaderCode(String code);

    Optional<TblRptoHeaderLevel1> findByHeaderName(String headerName);

    Optional<TblRptoHeaderLevel1> findByHeaderOrder(Integer order);
}
