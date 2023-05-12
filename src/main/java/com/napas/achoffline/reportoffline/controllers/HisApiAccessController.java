/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.entity.TblUserToken;
import com.napas.achoffline.reportoffline.models.HisApiAccessDTO;
import com.napas.achoffline.reportoffline.repository.TblUserTokenRepository;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author HuyNX
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hisapiaccess")
public class HisApiAccessController {

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<HisApiAccessDTO> searchPaging(
            @RequestParam(name = "begindate", required = false) String beginDate,
            @RequestParam(name = "enddate", required = false) String endDate,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "method", required = false) String method,
            @RequestParam(name = "page", required = false) String pageIndex,
            @RequestParam(name = "pagesize", required = false) String pageSize) throws ParseException {
        return hisAccessService.searchPaging(username, method, beginDate, endDate, Integer.parseInt(pageIndex) - 1, Integer.parseInt(pageSize));
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    @GetMapping("subjects")
    public List<String> listExistingSubjects() {
        return null;
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
}
