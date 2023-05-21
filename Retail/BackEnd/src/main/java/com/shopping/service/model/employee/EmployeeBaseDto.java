package com.shopping.service.model.employee;

import com.shopping.service.model.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBaseDto extends BaseDto<Long> {

    private Long user;

    private Long department;

    private Integer salary;

    private String jobDescription;

    private LocalDate jobBeginDate;

    private LocalDate jobEndDate;

    private String address;

    private Long parent;



}
