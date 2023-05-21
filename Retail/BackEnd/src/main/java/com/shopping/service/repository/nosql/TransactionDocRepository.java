package com.shopping.service.repository.nosql;

import com.shopping.service.document.TransactionDocument;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface TransactionDocRepository extends MongoRepository<TransactionDocument, Long> {

    @Aggregation(pipeline = {"{$match: {date: {$gte: ?0}}}","{$group: { _id: '', total: {$sum: $total}}}"})
    double getTotal(LocalDateTime from);

}
