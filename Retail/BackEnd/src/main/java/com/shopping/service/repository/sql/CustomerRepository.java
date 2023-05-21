package com.shopping.service.repository.sql;

import com.shopping.service.entity.Customer;
import com.shopping.service.model.customer.CustomerProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c.id as id, u.username as username from Customer c join c.user u where lower(u.username) like lower(concat('%', :name, '%') )")
    List<CustomerProj> getCustomers(String name);

    @Query("select count(c.id) from Customer c")
    int getNumOfCustomers();

}
