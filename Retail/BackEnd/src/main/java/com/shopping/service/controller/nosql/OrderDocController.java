package com.shopping.service.controller.nosql;

import com.shopping.service.document.OrderDocument;
import com.shopping.service.services.nosql.order.OrderDocService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nosql/orders")
@RequiredArgsConstructor
public class OrderDocController {

    private final OrderDocService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/join")
    public ResponseEntity<?> save(@Valid @RequestBody OrderDocument dto){
        return ResponseEntity.ok(service.doOrder(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody OrderDocument dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

}
