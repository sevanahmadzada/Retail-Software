package com.shopping.service.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDocReport {
    private Long id;
    private String productName;
    private String description;
    private String price;
    private int quantity;

}
