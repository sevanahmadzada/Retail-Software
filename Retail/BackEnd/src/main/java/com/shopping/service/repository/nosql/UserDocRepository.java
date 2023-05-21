package com.shopping.service.repository.nosql;

import com.shopping.service.document.UserDocument;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import java.util.List;

public interface UserDocRepository extends MongoRepository<UserDocument, Long> {



    @Query("{ 'username': {$regex: ?0, $options: 'i' } }")
    List<UserDocument> findByUsername(String username);


}
