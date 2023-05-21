package com.shopping.service.document;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sequence")
public class  Sequence {
    @Id
    private String id;

    private long seq;

}
