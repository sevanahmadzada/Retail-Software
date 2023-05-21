package com.shopping.service.repository.nosql;

import com.shopping.service.document.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CustomerDocRepository extends MongoRepository<CustomerDocument,Long> {

}
