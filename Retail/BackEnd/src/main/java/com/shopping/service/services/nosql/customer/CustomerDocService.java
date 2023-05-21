package com.shopping.service.services.nosql.customer;

import com.shopping.service.model.customer.CustomerNameDto;
import com.shopping.service.model.customer.CustomerProj;
import com.shopping.service.repository.nosql.CashierDocRepository;
import com.shopping.service.repository.nosql.CustomerDocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerDocService {

    private final CustomerDocRepository repository;
    private final MongoTemplate template;

    public long numberOfCustomers() {
        return repository.count();
    }

    public List<?> getCustomersLikeUserName(String name) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("User", "_id", "_id","User"),
                Aggregation.unwind("User"),
                Aggregation.match(Criteria.where("User.username").regex(name)),
                Aggregation.project("_id", "$User.username")
        );

        AggregationResults<CustomerNameDto> results = template.aggregate(aggregation, "Customer", CustomerNameDto.class);
        return results.getMappedResults();
    }

}
