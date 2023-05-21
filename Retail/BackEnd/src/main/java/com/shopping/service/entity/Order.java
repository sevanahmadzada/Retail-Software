package com.shopping.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_tbl")
public class Order extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private Float discount;

    private Double total;

    private String content;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order(Long id){
        this.setId(id);
    }

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        transaction.setOrder(this);
    }

}
