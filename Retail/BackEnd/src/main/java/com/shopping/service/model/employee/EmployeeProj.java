package com.shopping.service.model.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface EmployeeProj {

    Long getId();

    @JsonProperty(value = "name")
    String getNm();

    String getSurname();

    String getUsername();

    @JsonProperty(value = "departmentName")
    String getDepName();

    int getSumma();

}
