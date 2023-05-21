package com.shopping.service.model.product;


import com.shopping.service.model.BaseDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBaseDto extends BaseDto<Long> {

    private String description;

//    @NotEmpty
    private String name;

    private double price;

    private Integer quantity;

    private Float discount;

    private Long addedBy;

}
