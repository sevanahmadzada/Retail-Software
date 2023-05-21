package com.shopping.service.model.sec_dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class JwtRequest {

    @NotBlank(/*message = "{not.blank}"*/)
    private String username;
    @NotBlank(/*message = "{not.blank}"*/)
    private String password;

}
