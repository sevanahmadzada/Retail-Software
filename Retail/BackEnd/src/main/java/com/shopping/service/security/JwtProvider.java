package com.shopping.service.security;

import com.shopping.service.entity.Role;
import com.shopping.service.entity.User;
import com.shopping.service.model.sec_dto.RoleTokenDto;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
public class JwtProvider {

    private final String jwtAccessSecret;
    private final String jwtRefreshSecret;
    private final String accessExpTime;
    private final String refreshExpTime;
    private final ModelMapper mapper;

    @Autowired
    public JwtProvider(
            ModelMapper mapper,
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
            @Value("${jwt.access.exp.time}") String accessExpTime,
            @Value("${jwt.refresh.exp.time}") String refreshExpTime
    ) {
        this.mapper = mapper;
        this.accessExpTime = accessExpTime;
        this.refreshExpTime = refreshExpTime;
        this.jwtAccessSecret = jwtAccessSecret;
        this.jwtRefreshSecret = jwtRefreshSecret;
    }

    public String generateAccessToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(Long.parseLong(accessExpTime))
                .atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        HashSet<RoleTokenDto> rolesDto = new HashSet<>();

        List<Role> roles = user.getRoles();
        for (Role r : roles){
            rolesDto.add(mapper.map(r, RoleTokenDto.class));
        }
//        System.out.println(rolesDto);

        final String accessToken = Jwts.builder()
//                .setId(String.valueOf(user.getId()))
                .setSubject(user.getUsername())
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .claim("userId", user.getId())
                .claim("roles", rolesDto)
                .compact();
        return accessToken;
    }

    public String generateRefreshToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusMinutes(Long.parseLong(refreshExpTime))
                .atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        final String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
        return refreshToken;
    }

    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String token) {
        return validateToken(token, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
//            throw new ApplicationException(Errors.UNSUPPORTED_JWT);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
