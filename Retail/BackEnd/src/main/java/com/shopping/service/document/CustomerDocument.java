package com.shopping.service.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopping.service.entity.Cashier;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "Customer")
public class CustomerDocument {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    private String customerContactNum;

    private String address;

    private String email;

    private LocalDate registerAt;

    private Long cashier;

    @DBRef
    private List<TransactionDocument> transactions;

}
