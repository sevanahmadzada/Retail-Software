package com.shopping.service.controller.nosql;

import com.shopping.service.services.nosql.customer.CustomerDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nosql/customers")
@RequiredArgsConstructor
public class CustomerDocController {

    private final CustomerDocService service;

    @GetMapping("/number")
    public ResponseEntity<?> getCount(){
        return ResponseEntity.ok(service.numberOfCustomers());
    }

    @GetMapping("/like")
    public ResponseEntity<?> getCustomersByName(@RequestParam String name){
        return ResponseEntity.ok(service.getCustomersLikeUserName(name));
    }


}
