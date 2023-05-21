package com.shopping.service.repository.nosql;

import com.shopping.service.document.OrderDetailDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderDetailsDocRepository extends MongoRepository<OrderDetailDocument, Long> {
}
