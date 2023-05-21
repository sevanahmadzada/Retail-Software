package com.shopping.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto <T>{

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private T id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modifiedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

}
