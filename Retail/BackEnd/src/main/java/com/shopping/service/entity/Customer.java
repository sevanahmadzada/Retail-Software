package com.shopping.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends BaseEntity<Long> {

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(length = 30)
    private String customerContactNum;

    @Column(length = 100)
    private String address;

    @Column(length = 50)
    private String email;

    @Column
    private LocalDate registerAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cashier cashier;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    public Customer(Long id){
        this.setId(id);
    }

}
