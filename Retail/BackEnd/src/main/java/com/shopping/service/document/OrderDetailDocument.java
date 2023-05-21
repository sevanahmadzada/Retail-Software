package com.shopping.service.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "OrderDetail")
public class OrderDetailDocument {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @Indexed
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long order;

    private Long product;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double price;

    private Float discount;

    private Integer quantity;
}
