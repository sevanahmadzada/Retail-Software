package com.shopping.service.repository.nosql;

import com.shopping.service.document.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductDocRepository extends MongoRepository<ProductDocument, Long> {
    Page<ProductDocument> findAllByStatus(char status, Pageable pageable);

    @Query("{'name' :{ $regex : ?0, $options: 'i'}}")
    List<ProductDocument> findAllByNameLike(String name);
}




