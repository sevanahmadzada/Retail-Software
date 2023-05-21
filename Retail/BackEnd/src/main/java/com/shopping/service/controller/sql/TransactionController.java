package com.shopping.service.controller.sql;

import com.shopping.service.model.transaction.TransactionBaseDto;
import com.shopping.service.services.sql.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sql/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody TransactionBaseDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TransactionBaseDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/monthlyTurnover")
    public ResponseEntity<?> monthlyTurnover(){
        return ResponseEntity.ok(service.monthlyTurnover());
    }

}
