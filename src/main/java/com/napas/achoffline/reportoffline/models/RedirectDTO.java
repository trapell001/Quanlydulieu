package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedirectDTO {
    private String clientId;
    private String redirectUri;
    private String codeChallenge;
    private String state;
    private String codeVerifier;
    private String authUri;
}
