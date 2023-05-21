package com.shopping.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends BaseEntity<Long> {

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dep_id")
    private Department department;

    private Integer salary;

    @Column(nullable = false, length = 300)
    private String jobDescription;

    private LocalDate jobBeginDate;

    private LocalDate jobEndDate;

    @Column(nullable = false, length = 150)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Employee parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Employee> children = new ArrayList<>();

    @OneToMany(mappedBy = "employee")
    private List<Order> orders = new ArrayList<>();


    @OneToMany(mappedBy = "addedBy")
    private List<Product> products = new ArrayList<>();

    @OneToOne(mappedBy = "employee", optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Cashier cashier;

    public Employee(Long id){
        this.setId(id);
    }

    public void addChild(Employee employee){
        children.add(employee);
        employee.setParent(this);
    }

    public void addProduct(Product product){
        products.add(product);
        product.setAddedBy(this);
    }

//    @ManyToMany
//    @JoinTable(name = "cashier_customer",
//            joinColumns = {@JoinColumn(name = "cashier_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "customer_id", referencedColumnName = "id")})
//    private List<Customer> customers = new ArrayList<>();

}
