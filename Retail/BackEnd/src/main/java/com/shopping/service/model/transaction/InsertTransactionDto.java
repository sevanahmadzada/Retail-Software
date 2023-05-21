package com.shopping.service.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertTransactionDto {

    @JsonProperty("order")
    private Long order;
    private String paymentType;

}
