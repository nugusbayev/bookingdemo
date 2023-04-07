package com.example.demo.common;

import com.example.demo.model.CurrentUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class HelperService {
    @Bean
    @RequestScope
    public CurrentUser currentUser() {
        return new CurrentUser();
    }
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String keycloakIssuerURI;
    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
                JwtDecoders.fromIssuerLocation(keycloakIssuerURI);

        OAuth2TokenValidator<Jwt> withClockSkew = new DelegatingOAuth2TokenValidator<>(
                new JwtIssuerValidator(keycloakIssuerURI));

        jwtDecoder.setJwtValidator(withClockSkew);

        return jwtDecoder;
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
