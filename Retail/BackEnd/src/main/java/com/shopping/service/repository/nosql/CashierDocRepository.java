package com.shopping.service.repository.nosql;

import com.shopping.service.document.CashierDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CashierDocRepository extends MongoRepository<CashierDocument,Long> {

}
