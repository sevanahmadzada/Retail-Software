package com.shopping.service.services.sql.customer;

import com.shopping.service.model.customer.CustomerProj;
import com.shopping.service.repository.sql.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public List<CustomerProj> getCustomersLikeUserName(String name) {
        return repository.getCustomers(name);
    }

    @Override
    public int numberOfCustomers() {
        return repository.getNumOfCustomers();
    }
}
