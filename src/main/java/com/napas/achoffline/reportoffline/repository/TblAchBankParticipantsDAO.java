/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.napas.achoffline.reportoffline.repository;

import com.napas.achoffline.reportoffline.entity.TblAchBankParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author huynx
 */
@Repository
public interface TblAchBankParticipantsDAO extends JpaRepository<TblAchBankParticipants, Integer> {

}
