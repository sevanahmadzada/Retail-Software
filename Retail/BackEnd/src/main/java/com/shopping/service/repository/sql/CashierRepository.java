package com.shopping.service.repository.sql;

import com.shopping.service.entity.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashierRepository extends JpaRepository<Cashier, Long> {
}
