package com.shopping.service.controller.sql;

import com.shopping.service.model.order.OrderBaseDto;
import com.shopping.service.model.order.OrderDto;
import com.shopping.service.services.sql.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sql/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody OrderBaseDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody OrderBaseDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PutMapping("/join")
    public ResponseEntity<?> doOrder(@Valid @RequestBody OrderDto dto){
        return ResponseEntity.ok(service.saveJoin(dto));
    }

}
