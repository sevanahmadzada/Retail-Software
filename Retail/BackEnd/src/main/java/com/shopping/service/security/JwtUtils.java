package com.shopping.service.security;

import com.shopping.service.entity.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims) {

        final List roles = claims.get("roles", List.class);
        final Set<Role> rolesSet = new HashSet<>();


        for (Object o : roles) {

            LinkedHashMap<String, String> l = (LinkedHashMap<String, String>) o;
            Role r = new Role();

            r.setRollName(l.get("rollName"));
            rolesSet.add(r);
        }

        return rolesSet;

    }

}