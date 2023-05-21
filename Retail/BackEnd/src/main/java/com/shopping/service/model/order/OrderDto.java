package com.shopping.service.model.order;

import com.shopping.service.model.BaseDto;
import com.shopping.service.model.order_detail.OrderDetailBaseDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends BaseDto<Long> {

    private String content;

    @NotNull
    private Long employee;

    private Long customer;

    private List<OrderDetailBaseDto> orderDetailDto = new ArrayList<>();

}
