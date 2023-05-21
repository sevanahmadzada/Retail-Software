package com.shopping.service.services.sql.customer;

import com.shopping.service.model.customer.CustomerProj;

import java.util.List;

public interface CustomerService {
    List<CustomerProj> getCustomersLikeUserName(String name);

    int numberOfCustomers();
}
