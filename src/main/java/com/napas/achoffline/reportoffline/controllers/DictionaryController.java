/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.entity.AchSessions;
import com.napas.achoffline.reportoffline.entity.TblUserToken;
import com.napas.achoffline.reportoffline.repository.TblUserTokenRepository;
import com.napas.achoffline.reportoffline.service.AchSessionService;
import java.util.List;

import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author HuyNX
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dictionary")
@Slf4j
public class DictionaryController {

    @Autowired
    private AchSessionService achSessionService;

    @Autowired
    private TblUserTokenRepository tblUserTokenRepository;

    @Autowired
    private HisApiAccessService hisAccessService;
    private void logAccess(HttpServletRequest request, String param, String addInfo) {
        String token = request.getHeader("Authorization");
        token = token.split(" ")[1];
        TblUserToken tblUserToken= tblUserTokenRepository.findByToken(token);
        hisAccessService.logAccess(request.getMethod(), request.getRemoteAddr(),
                tblUserToken.getUsername(), request.getRequestURI(), param,addInfo);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/sessions", produces = "application/json; charset=UTF-8")
    public List<AchSessions> listSessions(HttpServletRequest request,@RequestParam(name = "type") String sessionType) {
        List<AchSessions> listSession = achSessionService.listSessions(Long.valueOf(sessionType));
        return listSession;
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/currentTime")
    public ResponseEntity<?> currentTime() {
        return ResponseEntity.ok(LocalDateTime.now());
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/currentSession")
    public ResponseEntity<?> currentSession(@RequestParam(name = "type") long sessionType) {
        return ResponseEntity.ok(achSessionService.findCurrentSession(sessionType));
    }
}
