package com.shopping.service.model.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerNameDto {

    @JsonProperty("id")
    private Long _id;

    @JsonProperty("username")
    private String username;

}
