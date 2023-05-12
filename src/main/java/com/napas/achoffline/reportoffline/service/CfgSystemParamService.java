/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import com.napas.achoffline.reportoffline.entity.CfgSystemParam;
import com.napas.achoffline.reportoffline.payload.response.MessageResponse;
import com.napas.achoffline.reportoffline.repository.CfgSystemParamDAO;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author HuyNX
 */
@Service
public class CfgSystemParamService {

    @Autowired
    private CfgSystemParamDAO sysParamDAO;

    public List<CfgSystemParam> get() {
        Sort sort = Sort.by("paramGroup").and(Sort.by("name"));
        return sysParamDAO.findAll(sort);
    }

    public CfgSystemParam get(String group, String name) {
        return sysParamDAO.findByParamGroupAndName(group, name).get();
    }

    public List<CfgSystemParam> get(String group) {
        Sort sort = Sort.by("paramOrder");
        return sysParamDAO.findByParamGroup(sort, group);
    }

    public ResponseEntity<?> put(String group, String name, CfgSystemParam input) {
        CfgSystemParam current = sysParamDAO.findByParamGroupAndName(group, name).get();
        current.setModifDate(new Date());
        current.setVal(input.getVal());
        sysParamDAO.save(current);
        return ResponseEntity.ok(new MessageResponse(String.format("Cập nhật tham số '%s' thành công", current.getTitle())));
    }
}
