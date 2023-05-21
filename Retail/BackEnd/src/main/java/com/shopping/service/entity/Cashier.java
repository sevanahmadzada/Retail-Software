package com.shopping.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Cashier extends BaseEntity<Long> {

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Employee employee;

    private Integer numberTimesLackMoney;

    @OneToMany(mappedBy = "cashier")
    private List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer){
        customers.add(customer);
        customer.setCashier(this);
    }
}
