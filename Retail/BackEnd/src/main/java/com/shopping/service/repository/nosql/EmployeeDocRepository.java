package com.shopping.service.repository.nosql;

import com.shopping.service.document.EmployeeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeDocRepository extends MongoRepository<EmployeeDocument,Long> {
}
