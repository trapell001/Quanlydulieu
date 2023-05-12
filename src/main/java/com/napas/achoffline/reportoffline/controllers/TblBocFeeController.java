/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.napas.achoffline.reportoffline.controllers;
import com.napas.achoffline.reportoffline.entity.TblBocFee;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblBocFeeService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/TblBocFee")
public class TblBocFeeController {

    @Autowired
    private TblBocFeeService tblBocFeeService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    public List<TblBocFee> list(HttpServletRequest httpServletRequest) {
        return tblBocFeeService.getAll();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    public Optional<TblBocFee> get(@PathVariable Long id, HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblBocFeeService.get(id);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @PostMapping("/")
    public ResponseEntity<?> insert(@RequestBody TblBocFee tblBocFee,HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblBocFeeService.insert(tblBocFee);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TblBocFee tblBocFee,HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblBocFeeService.update(id,tblBocFee);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblBocFeeService.delete(id);
    }
}
