package com.shopping.service.security.service;

import com.shopping.service.entity.User;
import com.shopping.service.model.sec_dto.JwtRequest;
import com.shopping.service.model.sec_dto.JwtResponse;
import com.shopping.service.security.JwtAuthentication;
import com.shopping.service.security.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserJwtService userJWTService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws Exception {
        final User user = userJWTService.getByUserName(authRequest.getUsername())
                .orElseThrow(
//                        () -> new ApplicationException(Errors.USERNAME_NOT_FOUND)
                );

        if (new BCryptPasswordEncoder().matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getUsername(), refreshToken);
            return new JwtResponse(user.getId(), user.getUsername(), accessToken, refreshToken);
        } else {
//            throw new ApplicationException(Errors.INCORRECT_PASSWORD);
            throw new AuthException("Incorrect password!!!");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(username);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userJWTService.getByUserName(username)
                        .orElseThrow(() -> new AuthException("User not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.remove(user.getUsername());
                refreshStorage.put(user.getUsername(), refreshToken);
                return new JwtResponse(user.getId(), user.getUsername(), accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Invalid JWT token");
    }

//    public JwtResponse refresh(@NonNull String refreshToken) {
//        if (jwtProvider.validateRefreshToken(refreshToken)) {
//            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
//            final String username = claims.getSubject();
//            final String saveRefreshToken = refreshStorage.get(username);
//            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
//                final User user = userJWTService.getByUsername(username)
//                        .orElseThrow(() -> new AuthException("User not found"));
//
//                final String accessToken = jwtProvider.generateAccessToken(user);
//                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
//                refreshStorage.put(user.getUsername(), newRefreshToken);
//                return new JwtResponse(null, newRefreshToken);
//            }
//        }
//        throw new AuthException("Invalid JWT token");
//    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
