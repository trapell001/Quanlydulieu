

package com.napas.achoffline.reportoffline.service;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.napas.achoffline.reportoffline.entity.HisAlertConfig;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.HisAlertConfigDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author HuyNX
 */
@Service
public class HisAlertConfigService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private HisAlertConfigDAO hisAlertConfigDAO;


    public Page<HisAlertConfig> getAllElements(int page, int pageSize , String alertId, String alertName) {
        Pageable sortedById = PageRequest.of(page, pageSize, Sort.by("createdDate").ascending());
        Page<HisAlertConfig> hisAlertConfigPage = hisAlertConfigDAO.getAllElements(sortedById,alertId,alertName);
        return hisAlertConfigPage;
    }
    public Optional<HisAlertConfig> get(Long id) {
        return hisAlertConfigDAO.findById(id);
    }
    public ResponseEntity<?> insert(HisAlertConfig hisAlertConfig) {
            hisAlertConfig.setAlertCode(hisAlertConfig.getAlertCode().trim());
            if(hisAlertConfigDAO.findByAlertCode(hisAlertConfig.getAlertCode()).isEmpty()){
                hisAlertConfigDAO.save(hisAlertConfig);
                return ResponseEntity.ok(new MessageResponse("Thêm cảnh báo email thành công"));
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new MessageResponse("Mã cảnh báo đã tồn tại"));
            }
    }
    public ResponseEntity<?> update(long id, HisAlertConfig hisAlertConfig) {
        hisAlertConfig.setAlertCode(hisAlertConfig.getAlertCode().trim());
        if (check(id)==true) {
            if(hisAlertConfigDAO.findByAlertCode(hisAlertConfig.getAlertCode()).isEmpty()||
                    hisAlertConfigDAO.findById(id).get().getAlertCode().equals(hisAlertConfig.getAlertCode())){
                hisAlertConfig.setId(id);
                hisAlertConfigDAO.save(hisAlertConfig);
                return ResponseEntity.ok(hisAlertConfig);
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new MessageResponse("Mã cảnh báo đã tồn tại"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền cập nhật"));
        }
    }
    public ResponseEntity<?> delete(Long id) {
        if(check(id)){
            hisAlertConfigDAO.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Xoá cảnh báo thành công "));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền xóa"));
        }
    }
    public boolean check(Long id){
        boolean it = false;
        List<GrantedAuthority> userMaker = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().collect(Collectors.toList());
        for (GrantedAuthority g : userMaker) {
            if (g.getAuthority().equals("ADMIN") || g.getAuthority().equals("ROOT")||g.getAuthority().equals("OPERATOR")||g.getAuthority().equals("SENIOR_OPERATOR")) {
                it = true;
            }
        }
        return it;
    }
}

