/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.define.QtbsPaymentStatus;
import com.napas.achoffline.reportoffline.models.TblFeeQtbsDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblFeeQtbsService;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SignatureException;
import java.util.Date;

import com.napas.achoffline.reportoffline.utils.LogApiAccess;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static com.napas.achoffline.reportoffline.service.LogInterceptor.checkSignature;

/**
 *
 * @author huynx
 */
@RestController
@RequestMapping("/api/TblFeeQtbs")
public class TblFeeQtbsController {

    @Autowired
    private TblFeeQtbsService tblFeeQtbsService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public Page<TblFeeQtbsDTO> find(HttpServletRequest request,
            @RequestParam(name = "page", required = false) Integer pageIndex,
            @RequestParam(name = "pagesize", required = false) Integer pageSize,
            @RequestParam(name = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateFrom,
            @RequestParam(name = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateTo,
            @RequestParam(name = "sessionFrom", required = false) Long sessionFrom,
            @RequestParam(name = "sessionTo", required = false) Long sessionTo,
            @RequestParam(name = "bank", required = false) String bank,
            @RequestParam(name = "status", required = false) QtbsPaymentStatus status
    ) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), LogApiAccess.logAccess(request),"");
        return tblFeeQtbsService.find(pageIndex, pageSize, dateFrom, dateTo,
                sessionFrom, sessionTo, bank, status);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'TRA_SOAT', 'KIEM_SOAT')")
    public TblFeeQtbsDTO get(HttpServletRequest request,@PathVariable long id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblFeeQtbsService.get(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> put(HttpServletRequest request1,@PathVariable long id, @RequestBody TblFeeQtbsDTO input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request1.getMethod(), request1.getRemoteAddr(),
                principal.getName(), request1.getRequestURI(), "",input.toString());
        if (checkSignature(request1)){
            return tblFeeQtbsService.put(id, input);
        } else return ResponseEntity.ok("Sai chữ ký");
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> post(HttpServletRequest request1, @RequestBody TblFeeQtbsDTO input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request1.getMethod(), request1.getRemoteAddr(),
                principal.getName(), request1.getRequestURI(), "",input.toString());
        if (checkSignature(request1)){
            return tblFeeQtbsService.post(input);
        } else return ResponseEntity.ok("Sai chữ ký");

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TRA_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> delete(HttpServletRequest request,@PathVariable long id) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblFeeQtbsService.delete(id);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

}
