package com.napas.achoffline.reportoffline.service;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.napas.achoffline.reportoffline.entity.HisAlertConfig;
import com.napas.achoffline.reportoffline.entity.HisAlertEmailConfig;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.HisAlertConfigDAO;
import com.napas.achoffline.reportoffline.repository.HisAlertEmailConfigDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author HuyNX
 */
@Service
public class HisAlertEmailConfigService {
    @Autowired
    private HisAlertEmailConfigDAO hisAlertEmailConfigDAO;
    @Autowired
    private HisAlertConfigDAO hisAlertConfigDAO;
    public Page<HisAlertEmailConfig> getAllElements(int page, int pageSize , String alertName,String emailReceive, String participant){
        Pageable sortedById = PageRequest.of(page, pageSize, Sort.by("createdDate").ascending());
        Page<HisAlertEmailConfig> hisAlertEmailConfigPage = hisAlertEmailConfigDAO.getAllElements(sortedById,alertName,emailReceive,participant);
        return hisAlertEmailConfigPage;
    }
    public Optional<HisAlertEmailConfig> get(Long id){
        return hisAlertEmailConfigDAO.findById(id);
    }
    public ResponseEntity<?> insert(HisAlertEmailConfig hisAlertEmailConfig) {
        boolean it = false;
        boolean vanHanh = false;
        List<GrantedAuthority> userMaker = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().collect(Collectors.toList());
        for (GrantedAuthority g : userMaker) {
            if (g.getAuthority().equals("KIEM_SOAT") || g.getAuthority().equals("TRA_SOAT") || g.getAuthority().equals("SENIOR_OPERATOR") || g.getAuthority().equals("OPERATOR")) {
                vanHanh = true;
            }
            if (g.getAuthority().equals("ADMIN") || g.getAuthority().equals("ADMIN")) {
                it = true;
            }
        }
        Optional<HisAlertConfig> hisAlertConfig = hisAlertConfigDAO.findByAlertCode(hisAlertEmailConfig.getAlertCode());
        if ((vanHanh == true || it == true) && hisAlertConfig.get().getAlertType() == 1) {
            if (hisAlertEmailConfigDAO.findAllByAlertCode(hisAlertConfig.get().getAlertCode())
                    .stream().filter(h -> hisAlertEmailConfig.getParticipant().equals(h.getParticipant()))
                    .collect(Collectors.toList())
                    .isEmpty()) {
                hisAlertEmailConfig.setAlertType(hisAlertConfig.get().getAlertType());
                hisAlertEmailConfigDAO.save(hisAlertEmailConfig);
                return ResponseEntity.ok(hisAlertEmailConfig);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new MessageResponse("Mã cảnh báo đã tồn tại"));            }
        }
        if ((vanHanh == false || it == true) && hisAlertConfig.get().getAlertType() == 0) {
            if (hisAlertEmailConfigDAO.findAllByAlertCode(hisAlertConfig.get().getAlertCode())
                    .stream().filter(h -> hisAlertEmailConfig.getParticipant().equals(h.getParticipant()))
                    .collect(Collectors.toList())
                    .isEmpty()
            ) {

                hisAlertEmailConfig.setAlertType(hisAlertConfig.get().getAlertType());
                hisAlertEmailConfigDAO.save(hisAlertEmailConfig);
                return ResponseEntity.ok(hisAlertEmailConfig);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new MessageResponse("Mã cảnh báo đã tồn tại"));            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền thêm cấu hình"));
        }
    }
    public ResponseEntity<?> update(Long id, HisAlertEmailConfig hisAlertEmailConfig){
        hisAlertEmailConfig.setAlertCode(hisAlertEmailConfig.getAlertCode().trim());
        Optional<HisAlertConfig> hisAlertConfig = hisAlertConfigDAO.findByAlertCode(hisAlertEmailConfig.getAlertCode());
        Optional<HisAlertEmailConfig> hisAlertEmailConfigCheck  = hisAlertEmailConfigDAO.findById(id);
        if (check(id)) {
            if (hisAlertEmailConfigDAO.findAllByAlertCode(hisAlertEmailConfig.getAlertCode())
                    .stream().filter(h -> hisAlertEmailConfig.getParticipant().equals(h.getParticipant()))
                    .collect(Collectors.toList())
                    .isEmpty()||hisAlertEmailConfigCheck.get().getParticipant().equals(hisAlertEmailConfig.getParticipant())
            ) {
                hisAlertEmailConfig.setId(id);
                hisAlertEmailConfig.setAlertType(hisAlertConfig.get().getAlertType());
                hisAlertEmailConfig.setCreatedDate(hisAlertEmailConfigCheck.get().getCreatedDate());
                hisAlertEmailConfigDAO.save(hisAlertEmailConfig);
                return ResponseEntity.ok(hisAlertEmailConfig);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new MessageResponse("Mã cảnh báo đã tồn tại"));            }

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền cập nhật"));
        }

    }
    public ResponseEntity<?> delete(Long id) {
        if(check(id)) {
            hisAlertEmailConfigDAO.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Xoá cảnh báo thành công "));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền xóa"));
        }
    }
    public ResponseEntity<?> saveEmail(String emailSend){
        List<HisAlertEmailConfig> listSaveEmail = hisAlertEmailConfigDAO.findAll();
        for (HisAlertEmailConfig hisAlertEmailConfig: listSaveEmail
        ) {
            hisAlertEmailConfig.setEmailSend(emailSend);
            hisAlertEmailConfigDAO.save(hisAlertEmailConfig);
        }
        return ResponseEntity.ok(new MessageResponse("Cập nhật email thành công"));
    }
    public boolean check(Long id){
        boolean it = false;
        boolean vanHanh = false;
        Optional<HisAlertEmailConfig> hisAlertEmailConfig = hisAlertEmailConfigDAO.findById(id);
        List<GrantedAuthority> userMaker = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().collect(Collectors.toList());
        for (GrantedAuthority g : userMaker) {
            if (g.getAuthority().equals("KIEM_SOAT") || g.getAuthority().equals("TRA_SOAT")|| g.getAuthority().equals("SENIOR_OPERATOR")|| g.getAuthority().equals("OPERATOR")) {
                vanHanh = true;
            }
            if (g.getAuthority().equals("ADMIN") || g.getAuthority().equals("ROOT")) {
                it= true;
            }
        }
        if (((hisAlertEmailConfig.get().getAlertType() == 1) && (it ==true||vanHanh==true)) || ((hisAlertEmailConfig.get().getAlertType() == 0 && it == true))) {
            return true;
        }
        else return false;
    }
}
