package com.napas.achoffline.reportoffline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
public class ACHOfflineAuthenticationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ACHOfflineAuthenticationApplication.class, args);
    }
}
