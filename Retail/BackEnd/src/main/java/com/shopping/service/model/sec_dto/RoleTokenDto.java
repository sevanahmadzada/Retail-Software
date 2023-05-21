package com.shopping.service.model.sec_dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Data
@ToString
public class RoleTokenDto implements GrantedAuthority {
    private Integer id;
    private String rollName;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return rollName;
    }
}