package com.napas.achoffline.reportoffline.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.napas.achoffline.reportoffline.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@Service
@Slf4j
public class Oauth2Service  extends BaseService{

    @Value("${oauth2.clientId}")
    private String clientId;

    @Value("${oauth2.clientSecret}")
    private String clientSecret;

    @Value("${oauth2.redirectUri}")
    private String redirectUri;

    @Value("${oauth2.url}")
    private String url;

    @Value("${oauth2.invalidateToken}")
    private String invalidateToken;

    @Value("${oauth2.urlInspectToken}")
    private String urlInspectToken;

    @Value("${oauth2.authUri}")
    private String authUri;

    private String codeVerifier;

    public ResponseEntity<?> generateToken(DataGenerateToken dataGenerateToken) throws URISyntaxException, JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        RestTemplate restTemplate = restTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        dataGenerateToken.setCodeVerifier(codeVerifier);
        dataGenerateToken.setRedirectUri(redirectUri);
        HttpEntity<DataGenerateToken> entity = new HttpEntity<>(dataGenerateToken, headers);
        URI uri = new URI(url);
        ResponseEntity<TokenDTO> response = restTemplate.exchange(
                uri, HttpMethod.POST, entity, TokenDTO.class);
        return ResponseEntity.ok(response.getBody());
    }

    public ResponseEntity<?> inspectToken(FromTokenDTO fromTokenDTO) throws URISyntaxException, JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        RestTemplate restTemplate = restTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        HttpEntity<FromTokenDTO> entity = new HttpEntity<>(fromTokenDTO, headers);
        URI uri = new URI(urlInspectToken);
        ResponseEntity<UserDetailsDTO> response = restTemplate.exchange(
                uri, HttpMethod.POST, entity, UserDetailsDTO.class);
        return ResponseEntity.ok(response.getBody());
    }
    public ResponseEntity<?> invalidateToken(@RequestBody FromTokenDTO fromTokenDTO) throws URISyntaxException, JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        RestTemplate restTemplate = restTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        HttpEntity<FromTokenDTO> entity = new HttpEntity<>(fromTokenDTO, headers);
        URI uri = new URI(invalidateToken);
        ResponseEntity response = restTemplate.exchange(
                uri, HttpMethod.POST, entity, Object.class);
        return ResponseEntity.ok("logout suscess");
    }

    public ResponseEntity<?> redirect() throws NoSuchAlgorithmException {
        RedirectDTO redirectDTO = new RedirectDTO();
        redirectDTO.setClientId(clientId);
        redirectDTO.setRedirectUri(redirectUri);
        redirectDTO.setState(generateSecret(32));
        redirectDTO.setAuthUri(authUri);
        codeVerifier = generateSecret(32);
        redirectDTO.setCodeVerifier(codeVerifier);
        redirectDTO.setCodeChallenge(generateOauthCodeChallenge(codeVerifier));
        return ResponseEntity.ok(redirectDTO);
    }


    private String generateSecret(int size) {
        byte[] byteArray = new byte[size];
        Random random = new Random();
        random.nextBytes(byteArray);
        return HexFormat.of().formatHex(byteArray);
    }

    public String generateOauthCodeChallenge(@NotNull String codeVerifier) throws NoSuchAlgorithmException {
        return Base64.getUrlEncoder()
                .encodeToString(
                        MessageDigest.getInstance("SHA-256")
                                .digest(codeVerifier.getBytes())
                );
    }

}
