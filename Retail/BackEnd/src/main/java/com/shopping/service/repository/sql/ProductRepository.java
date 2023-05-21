package com.shopping.service.repository.sql;

import com.shopping.service.entity.Product;
import com.shopping.service.model.product.ProductReportProj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByStatus(Character status, Pageable pageable);

    @Query(value = "select p.id as id, p.\"name\" as productName, p.description as description, p.price as price , sum(od.quantity) as quantity   \n" +
            "\tfrom product p \n" +
            "\tinner join order_detail od on p.id = od.product_id \n" +
            "\tinner join order_tbl o on o.id  = od.order_id \n" +
            "\tinner join \"transaction\" t on t.order_id = o.id and t.status ='1'\n" +
            "\tWHERE (t.\"date\"\\:\\:date  >= (CURRENT_TIMESTAMP - interval '1 week')\\:\\:date\n" +
            "      )\n" +
            "\tgroup by p.id  \n" +
            " order by quantity desc, p.id \n" +
            " limit 10 ", nativeQuery = true)
    List<ProductReportProj> getTop10Products();

    @Query("select sum(p.quantity*p.price) from Product p")
    double getTotalSumOfProducts();

    @Query("select p from Product p where lower(p.name) like lower(concat('%', :name, '%' ))")
    List<Product> findAllByNameLike(String name);

}