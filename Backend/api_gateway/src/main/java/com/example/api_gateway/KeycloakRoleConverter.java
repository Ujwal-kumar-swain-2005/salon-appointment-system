package com.example.api_gateway;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

@SuppressWarnings("unchecked")
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(@SuppressWarnings("null") Jwt jwt) {

        Set<GrantedAuthority> authorities = new HashSet<>();

        var realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        if (realmAccess != null && realmAccess.get("roles") != null) {

            var roles = (Collection<String>) realmAccess.get("roles");

            roles.forEach(role ->
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
            );
        }

        var resourceAccess = (Map<String, Object>) jwt.getClaims().get("resource_access");

        if (resourceAccess != null) {
            resourceAccess.forEach((client, clientRolesObj) -> {
                var clientRoleMap = (Map<String, Object>) clientRolesObj;
                var clientRoles = (Collection<String>) clientRoleMap.get("roles");

                if (clientRoles != null) {
                    clientRoles.forEach(role ->
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                    );
                }
            });
        }

        return authorities;
    }
}