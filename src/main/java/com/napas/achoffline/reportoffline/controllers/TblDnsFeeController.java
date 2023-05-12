/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.napas.achoffline.reportoffline.controllers;
import com.napas.achoffline.reportoffline.entity.TblDnsFee;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblDnsFeeService;

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
@RequestMapping("/api/TblDnsFee")
public class TblDnsFeeController {

    @Autowired
    private TblDnsFeeService tblDnsFeeService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    public List<TblDnsFee> list(HttpServletRequest httpServletRequest) {
        return tblDnsFeeService.getAll();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    public Optional<TblDnsFee> get(@PathVariable Long id, HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblDnsFeeService.get(id);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @PostMapping("/")
    public ResponseEntity<?> insert(@RequestBody TblDnsFee tblDnsFee,HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblDnsFeeService.insert(tblDnsFee);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TblDnsFee tblDnsFee,HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblDnsFeeService.update(id,tblDnsFee);
    }
    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR', 'KIEM_SOAT', 'TRA_SOAT', 'SENIOR_RD', 'SENIOR_BUSINESS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,HttpServletRequest request) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return tblDnsFeeService.delete(id);
    }
}
