package com.company.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        log.info("KeycloakJwtAuthenticationConverter::convert");
        return new JwtAuthenticationToken(
                source,
                Stream.concat(
                        new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                        extractResourceRoles(source).stream()
                ).collect(Collectors.toSet())
        );
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        log.info("KeycloakJwtAuthenticationConverter::extractResourceRoles");
        var resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));
        if(resourceAccess.isEmpty()) return Set.of();

        var keycloakIAMSpringSecurity = (Map<String, List<String>>) resourceAccess.get("keycloak-iam-spring-security");
        if(keycloakIAMSpringSecurity == null || keycloakIAMSpringSecurity.isEmpty()) {
            return Set.of();
        }

        var roles = keycloakIAMSpringSecurity.get("roles");
        if(roles == null || roles.isEmpty()) return Set.of();

        return roles.stream().map(role -> new SimpleGrantedAuthority(
                    String.format("ROLE_%s",role.replace("-","_"))
                ))
                .collect(Collectors.toSet());
    }
}
