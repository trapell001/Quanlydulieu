/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.common;

import com.napas.achoffline.reportoffline.service.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author huynx
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // LogInterceptor áp dụng cho mọi URL.
        registry.addInterceptor(logInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/static/**",
                        "/error",
                        "/api/dictionary/**",
                        "/api/participants/**",
                        "/api/hisapiaccess/**",
                        "/api/role/**",
                        "/api/auth/**",
                        "/api/user/**",
                        "/api/rpttraffic/**",
                        "/api/reportoffline/listTransType/**",
                        "/api/reportoffline/list/**"
                );
    }
}
