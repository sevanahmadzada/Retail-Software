package com.shopping.service.controller.sql;

import com.shopping.service.model.product.ProductBaseDto;
import com.shopping.service.services.sql.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sql/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "modifiedAt", required = false) String sorting,
            @RequestParam(defaultValue = "desc", required = false) String direction) {
        return ResponseEntity.ok(service.getAll(page, size, sorting, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ProductBaseDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ProductBaseDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/top10")
    public ResponseEntity<?> top10(){
        return ResponseEntity.ok(service.report());
    }

    @GetMapping("/sumOfProducts")
    public ResponseEntity<?> total(){
        return ResponseEntity.ok(service.totalSumOfProducts());
    }

    @GetMapping("/like")
    public ResponseEntity<?> like(@RequestParam String name){
        return ResponseEntity.ok(service.search(name));
    }

}
