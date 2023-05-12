package com.napas.achoffline.reportoffline.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.napas.achoffline.reportoffline.models.*;
import com.napas.achoffline.reportoffline.service.Oauth2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Random;

@RestController
@RequestMapping("/api/oauth")
@Slf4j
public class Oauth2Controller {

    @Autowired
    private Oauth2Service oauth2Service;

    @PostMapping("/generateToken")
    public ResponseEntity<?> generateToken(@RequestBody DataGenerateToken dataGenerateToken)
            throws URISyntaxException, JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return oauth2Service.generateToken(dataGenerateToken);
    }
    @PostMapping("/invalidateToken")
    public ResponseEntity<?> invalidateToken(@RequestBody FromTokenDTO fromTokenDTO) throws URISyntaxException, JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return oauth2Service.invalidateToken(fromTokenDTO);
    }

    @PostMapping("/inspectToken")
    public ResponseEntity<?> inspectToken(@RequestBody FromTokenDTO fromTokenDTO)
            throws URISyntaxException, JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return oauth2Service.inspectToken(fromTokenDTO);
    }

    @GetMapping("/authorize")
    public ResponseEntity<?> redirect() throws NoSuchAlgorithmException {
        return oauth2Service.redirect();
    }

}
