package com.napas.achoffline.reportoffline.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private String accessToken;
    private String tokenType;
    private Date expireAt;
}
