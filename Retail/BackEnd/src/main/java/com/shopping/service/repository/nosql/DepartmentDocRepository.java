package com.shopping.service.repository.nosql;

import com.shopping.service.document.DepartmentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentDocRepository extends MongoRepository<DepartmentDocument,Long> {
}
