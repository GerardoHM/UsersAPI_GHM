package com.chakray.api.config;

import com.chakray.api.security.JwtFilter;
import com.chakray.api.service.JwtService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final JwtService jwtService;

    public FilterConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {

        FilterRegistrationBean<JwtFilter> bean =
                new FilterRegistrationBean<>();

        bean.setFilter(new JwtFilter(jwtService));
        bean.addUrlPatterns("/users/*");

        return bean;
    }
}