package com.shopping.service.controller.sql;

import com.shopping.service.model.order_detail.OrderDetailBaseDto;
import com.shopping.service.services.sql.order_detail.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sql/orderDetails")
@RequiredArgsConstructor
//this controller is only meant for testing
public class OrderDetailController {

    private final OrderDetailService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody OrderDetailBaseDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody OrderDetailBaseDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

}
