/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.stream.Collectors;

/**
 * @author huynx
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    public LogInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletRequest request2;
        String username = "";
        try {
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();

            if (authen != null) {
                Object principal = authen.getPrincipal();
                if (principal != null && principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    username = userDetails.getUsername();
                }
            }

        } catch (Exception ex) {
            log.error("Message: {}", ex.getMessage());
        }
        return true;
    }

    public  static boolean checkSignature(HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException, SignatureException, InvalidKeyException {
        ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
        String requestBody = new String(requestWrapper.getContentAsByteArray());
        String Signature = request.getHeader("Signature");
        byte[] hashDigest = getSHA256(requestBody);
        String hmacDigest = getHMAC512(hashDigest, "truong");

        if (hmacDigest.equals(Signature)){
            return true;
        }
        else return false;
    }

    public static byte[] getSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String getHMAC512(byte[] data, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(secretKeySpec);
        String hash = Base64.getEncoder().encodeToString(mac.doFinal(data));

        return hash;
    }
}
