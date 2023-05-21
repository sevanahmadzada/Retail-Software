package com.shopping.service.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Order")
public class OrderDocument {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @Indexed
    private Long customer;

    @Indexed
    private Long employee;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Float discount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double total;

    private String content;

    @JsonProperty("orderDetailDto")
    @DBRef
    private List<OrderDetailDocument> orderDetails = new ArrayList<>();

}
