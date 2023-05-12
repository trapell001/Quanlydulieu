/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.napas.achoffline.reportoffline.controllers;

import com.napas.achoffline.reportoffline.payload.request.ReviewRequest;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import com.napas.achoffline.reportoffline.service.TblFeeQtbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SignatureException;

import static com.napas.achoffline.reportoffline.service.LogInterceptor.checkSignature;

/**
 *
 * @author huynx
 */
@RestController
@RequestMapping("/api/TblFeeQtbsReview")
public class TblFeeQtbsReviewController {

    @Autowired
    private TblFeeQtbsService tblFeeQtbsService;

    @Autowired
    private HisApiAccessService hisAccessService;

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','KIEM_SOAT', 'KIEM_SOAT')")
    public ResponseEntity<?> put(HttpServletRequest request1, @PathVariable Integer id, @RequestBody ReviewRequest input) throws UnsupportedEncodingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication();
        hisAccessService.logAccess(request1.getMethod(), request1.getRemoteAddr(),
                principal.getName(), request1.getRequestURI(), "",input.toString());
        if (checkSignature(request1)){
            return tblFeeQtbsService.applyReview(id, input);
        } else return ResponseEntity.ok("Sai chữ ký");

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

}
