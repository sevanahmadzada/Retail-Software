package com.shopping.service.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopping.service.model.BaseDto;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "Cashier")
public class CashierDocument {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    private Integer numberTimesLackMoney;

}
