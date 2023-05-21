package com.shopping.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE product SET status= '0' where id=?")
public class Product extends BaseEntity<Long> {

    @Column(length = 100)
    private String description;

    @Column(length = 40, nullable = false, unique = true)
    private String name;

    private double price;

    private Integer quantity;

    private Float discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by")
    private Employee addedBy;

    private Character status = '1';

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Product(Long id){
        this.setId(id);
    }

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetails.add(orderDetail);
        orderDetail.setProduct(this);
    }

}
