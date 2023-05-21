package com.shopping.service.model.employee;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeSpecDto {

    private Long department;

    private Integer salary;

    private String jobDescription;

    private LocalDate jobBeginDate;

    private LocalDate jobEndDate;

    private String address;

    private Long parent;

    private UserDto info;

}
