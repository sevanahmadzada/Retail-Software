package com.shopping.service.controller.sql;

import com.shopping.service.services.sql.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sql/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/like")
    public ResponseEntity<?> getCustomersByName(@RequestParam String name){
        return ResponseEntity.ok(service.getCustomersLikeUserName(name));
    }

    @GetMapping("/number")
    public ResponseEntity<?> getCount(){
        return ResponseEntity.ok(service.numberOfCustomers());
    }

}
