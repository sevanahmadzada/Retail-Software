package com.shopping.service.repository.nosql;

import com.shopping.service.document.RoleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleDocRepository extends MongoRepository<RoleDocument, Long> {
}
