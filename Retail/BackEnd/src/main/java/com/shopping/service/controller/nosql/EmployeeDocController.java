package com.shopping.service.controller.nosql;

import com.shopping.service.services.nosql.employee.EmployeeDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nosql/employees")
@RequiredArgsConstructor
public class EmployeeDocController {

    private final EmployeeDocService service;

    @GetMapping("/top5")
    public ResponseEntity<?> monthlyTop5(){
        return ResponseEntity.ok(service.top5Cashiers());
    }

}
