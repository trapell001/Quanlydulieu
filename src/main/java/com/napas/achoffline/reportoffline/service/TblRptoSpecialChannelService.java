/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;


import com.napas.achoffline.reportoffline.entity.TblRptoSpecialChannel;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;

import java.util.List;
import java.util.Optional;

import com.napas.achoffline.reportoffline.repository.TblRptoSpecialChannelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author huynx
 */
@Service
public class TblRptoSpecialChannelService {

    @Autowired
    private TblRptoSpecialChannelDAO tblRptoSpecialChannelDAO;


    public List<TblRptoSpecialChannel> getAll() {
        return tblRptoSpecialChannelDAO.findAll();
    }

    public Optional<TblRptoSpecialChannel> get(Long id) {
        return tblRptoSpecialChannelDAO.findById(id);
    }

    public ResponseEntity<?> post(TblRptoSpecialChannel input) {
        try {
            input.setChannelId(input.getChannelId().trim());
            input.setChannelName(input.getChannelName().trim());
            tblRptoSpecialChannelDAO.save(input);
            return ResponseEntity.ok(input);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Trùng mã hoặc tên cảnh báo"));
        }
    }
    public ResponseEntity<?> put(Long id, TblRptoSpecialChannel input) {
        input.setId(id);
        input.setChannelId(input.getChannelId().trim());
        input.setChannelName(input.getChannelName().trim());
        try{
            return ResponseEntity.ok(tblRptoSpecialChannelDAO.save(input));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Trùng mã hoặc tên"));
        }
    }
    public ResponseEntity<?> delete(Long id) {
        tblRptoSpecialChannelDAO.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Xóa thành công"));
    }
}
