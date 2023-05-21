package com.shopping.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor()
@NoArgsConstructor
@Entity
public class Department extends BaseEntity<Long>{

    @Column(name = "dep_name", length = 50)
    private String name;

    @Column(name = "address", length = 120)
    private String address;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    public Department(Long id){
        this.setId(id);
    }

    public Department(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addEmployee(Employee employee){
        employee.setDepartment(this);
        employees.add(employee);
    }
}
