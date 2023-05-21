package com.shopping.service.services.sql.employee;

import com.shopping.service.entity.Employee;
import com.shopping.service.entity.User;
import com.shopping.service.mapper.EmployeeMapper;
import com.shopping.service.model.employee.EmployeeBaseDto;
import com.shopping.service.model.employee.EmployeeProj;
import com.shopping.service.model.employee.EmployeeSpecDto;
import com.shopping.service.repository.sql.EmployeeRepository;
import com.shopping.service.repository.sql.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    private final EmployeeMapper mapper;

    private final UserRepository userRepository;

    @Override
    public EmployeeBaseDto findById(Long id) {
        Employee employee = repository.findById(id).orElseThrow(()->new NullPointerException("Employee not found!!!"));
        return mapper.toBaseDto(employee);
    }

    @Override
    public List<EmployeeSpecDto> getAll() {
        List<Employee> employees = repository.findAll();
        return employees.stream().map(mapper::entityToSpec).toList();
    }

//    @Override
//    public EmployeeBaseDto save(EmployeeBaseDto dto) {
//        Employee employee = new Employee();
//        mapper.update(employee, dto);
//        User u = userRepository.getReferenceById(dto.getUser());
//
//        employee.setUser(u);
//        repository.save(employee);
//        return mapper.toBaseDto(employee);
//    }

    @Override
    public EmployeeBaseDto save(EmployeeBaseDto dto) {
        Employee employee = new Employee();
        mapper.update(employee, dto);
        User u = userRepository.getReferenceById(dto.getUser());

        employee.setUser(u);
        repository.save(employee);
        return mapper.toBaseDto(employee);
    }

    @Override
    public EmployeeBaseDto update(Long id, EmployeeBaseDto dto) {
        Employee employee = repository.findById(id).orElseThrow(()->new NullPointerException("Employee not found!!!"));
        User u = userRepository.findById(dto.getUser()).orElseThrow();
        mapper.update(employee, dto);
        employee.setId(dto.getUser());
        u.setEmployee(employee);
        employee.setUser(u);
        repository.save(employee);
        return mapper.toBaseDto(employee);
    }

    @Override
    public List<EmployeeProj> top5Cashiers() {
        return repository.top5Monthly();
    }
}
