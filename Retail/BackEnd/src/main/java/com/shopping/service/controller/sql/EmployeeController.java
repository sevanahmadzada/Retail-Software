package com.shopping.service.controller.sql;

import com.shopping.service.model.employee.EmployeeBaseDto;
import com.shopping.service.services.sql.employee.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sql/employees")
@RequiredArgsConstructor
//this controller is only meant for testing
public class EmployeeController {
    private final EmployeeService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EmployeeBaseDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EmployeeBaseDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/top5")
    public ResponseEntity<?> monthlyTop5(){
        return ResponseEntity.ok(service.top5Cashiers());
    }

}
