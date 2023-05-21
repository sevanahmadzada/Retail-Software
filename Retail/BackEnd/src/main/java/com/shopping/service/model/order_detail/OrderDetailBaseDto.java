package com.shopping.service.model.order_detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopping.service.model.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailBaseDto extends BaseDto<Long> {

    private Long product;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long order;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Float price;

    private Float discount;

    private Integer quantity;

}
