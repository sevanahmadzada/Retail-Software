package com.shopping.service.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopping.service.entity.PaymentType;
import com.shopping.service.model.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionBaseDto extends BaseDto<Long> {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long customer;

    private Long order;

    private PaymentType paymentType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double total;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime date;

}
