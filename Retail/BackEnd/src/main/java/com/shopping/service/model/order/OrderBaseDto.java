package com.shopping.service.model.order;


import com.shopping.service.model.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBaseDto extends BaseDto<Long> {

    private Float discount;

    private Double total;

    private String content;

    private Long employee;

    private Long customer;

}
