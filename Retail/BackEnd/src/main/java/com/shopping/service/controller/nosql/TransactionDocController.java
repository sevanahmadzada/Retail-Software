package com.shopping.service.controller.nosql;

import com.shopping.service.model.transaction.InsertTransactionDto;
import com.shopping.service.services.nosql.transaction.TransactionDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/nosql/transactions")
@RequiredArgsConstructor
public class TransactionDocController {

    private final TransactionDocService transactionDocService;


    @PostMapping
    public ResponseEntity<?> save(@RequestBody InsertTransactionDto insertTransactionDto){
        return ResponseEntity.ok(transactionDocService.save(insertTransactionDto));
    }

    @GetMapping("monthlyTurnover")
    public ResponseEntity<?> total(){
        return ResponseEntity.ok(transactionDocService.getTotal(LocalDateTime.now().minusMonths(
                1)));
    }

}
