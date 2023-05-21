package com.shopping.service.services.sql.employee;

import com.shopping.service.model.employee.EmployeeBaseDto;
import com.shopping.service.model.employee.EmployeeProj;
import com.shopping.service.model.employee.EmployeeSpecDto;

import java.util.List;

public interface EmployeeService {

    EmployeeBaseDto findById(Long id);

    List<EmployeeSpecDto> getAll();

    EmployeeBaseDto save(EmployeeBaseDto dto);

    EmployeeBaseDto update(Long id, EmployeeBaseDto dto);

    List<EmployeeProj> top5Cashiers();

}
