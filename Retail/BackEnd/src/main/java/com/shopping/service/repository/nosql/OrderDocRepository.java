package com.shopping.service.repository.nosql;

import com.shopping.service.document.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderDocRepository extends MongoRepository<OrderDocument, Long> {
}
