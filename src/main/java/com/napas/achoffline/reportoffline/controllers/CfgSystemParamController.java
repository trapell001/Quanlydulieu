/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.entity.CfgSystemParam;
import com.napas.achoffline.reportoffline.entity.TblUserToken;
import com.napas.achoffline.reportoffline.repository.TblUserTokenRepository;
import com.napas.achoffline.reportoffline.service.CfgSystemParamService;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SignatureException;
import java.util.List;

import static com.napas.achoffline.reportoffline.service.LogInterceptor.checkSignature;

/**
 *
 * @author HuyNX
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sysparam")
@Slf4j
public class CfgSystemParamController {

    @Autowired
    private CfgSystemParamService sysParamService;

    @Autowired
    private TblUserTokenRepository tblUserTokenRepository;

    @Autowired
    private HisApiAccessService hisAccessService;

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    @GetMapping()
    public List<CfgSystemParam> list(HttpServletRequest request) throws Exception{
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return sysParamService.get();
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    @GetMapping("/{group}/{name}")
    public CfgSystemParam get(HttpServletRequest request,@PathVariable String group, @PathVariable String name) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return sysParamService.get(group, name);
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    @GetMapping("/{group}")
    public List<CfgSystemParam> get(HttpServletRequest request,@PathVariable String group) {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                principal.getName(), request.getRequestURI(), "","");
        return sysParamService.get(group);
    }

    @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'SENIOR_OPERATOR')")
    @PutMapping("/{group}/{name}")
    public ResponseEntity<?> put(HttpServletRequest request1, @PathVariable String group, @PathVariable String name, @RequestBody CfgSystemParam input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request1.getMethod(), request1.getRemoteAddr(),
                principal.getName(), request1.getRequestURI(), "",input.toString());
        if (checkSignature(request1)){
            return sysParamService.put(group, name, input);
        } else return ResponseEntity.ok("Sai chữ ký");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

    private void logAccess(HttpServletRequest request, String param, String addInfo) {
        String token = request.getHeader("Authorization");
        token = token.split(" ")[1];
        TblUserToken tblUserToken= tblUserTokenRepository.findByToken(token);
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                tblUserToken.getUsername(), request.getRequestURI(), param,addInfo);
    }

}
