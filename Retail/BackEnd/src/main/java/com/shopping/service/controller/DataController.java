package com.shopping.service.controller;

import com.shopping.service.repository.nosql.TransactionDocRepository;
import com.shopping.service.repository.nosql.UserDocRepository;
import com.shopping.service.services.generator.DataMigrator;
import com.shopping.service.services.generator.DataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final DataGenerator dataGenerator;
    private final DataMigrator dataMigrator;


    @GetMapping("/generation")
    public ResponseEntity<?> generation(){
        dataGenerator.generateData();
        return ResponseEntity.ok("Data successfully generated to PostgreSql");
    }


    @PostMapping("/migration")
    public ResponseEntity<?> migration(){
        dataMigrator.deleteAll();
        dataMigrator.transfer();
        return ResponseEntity.ok("Data successfully generated to MongoDB");
    }

    @DeleteMapping("/deleteNoSql")
    public ResponseEntity<?> deleteAll(){
        dataMigrator.deleteAll();
        return ResponseEntity.ok("MongoDB database is clear");
    }

}
