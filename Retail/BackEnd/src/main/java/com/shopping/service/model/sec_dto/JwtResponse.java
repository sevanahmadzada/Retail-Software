package com.shopping.service.model.sec_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private Long userId;
    private String userName;
    private String accessToken;
    private String refreshToken;

}
