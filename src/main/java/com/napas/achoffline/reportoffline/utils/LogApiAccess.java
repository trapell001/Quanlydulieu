package com.napas.achoffline.reportoffline.utils;

import com.napas.achoffline.reportoffline.entity.TblUserToken;
import com.napas.achoffline.reportoffline.models.FromTokenDTO;
import com.napas.achoffline.reportoffline.models.UserDetailsDTO;
import com.napas.achoffline.reportoffline.service.HisApiAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Enumeration;

@Slf4j
public class LogApiAccess {

    @Value("${fis.clientId}")
    private static String clientId;

    @Value("${fis.clientSecret}")
    private static String clientSecret;
    private static RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private static HisApiAccessService hisAccessService;

    public static String logAccess(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        String s = "";
        while (parameterNames.hasMoreElements()) {

            String paramName = parameterNames.nextElement();
            s += paramName + "=";
            if(!request.getParameter(paramName).isEmpty())
                s += request.getParameter(paramName);
            s += "&";
        }
        s = s.substring(0,s.length() - 1);
        return s;
    }
}
