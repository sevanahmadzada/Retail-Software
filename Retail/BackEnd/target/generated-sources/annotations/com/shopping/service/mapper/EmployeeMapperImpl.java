package com.shopping.service.mapper;

import com.shopping.service.entity.Employee;
import com.shopping.service.entity.Employee.EmployeeBuilder;
import com.shopping.service.model.employee.EmployeeBaseDto;
import com.shopping.service.model.employee.EmployeeSpecDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-03T07:58:26+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class EmployeeMapperImpl extends EmployeeMapper {

    @Override
    public EmployeeBaseDto toBaseDto(Employee entity) {
        if ( entity == null ) {
            return null;
        }

        EmployeeBaseDto employeeBaseDto = new EmployeeBaseDto();

        employeeBaseDto.setDepartment( departmentToId( entity.getDepartment() ) );
        employeeBaseDto.setParent( parentToId( entity.getParent() ) );
        employeeBaseDto.setUser( userToId( entity.getUser() ) );
        employeeBaseDto.setId( entity.getId() );
        employeeBaseDto.setModifiedAt( entity.getModifiedAt() );
        employeeBaseDto.setCreatedAt( entity.getCreatedAt() );
        employeeBaseDto.setSalary( entity.getSalary() );
        employeeBaseDto.setJobDescription( entity.getJobDescription() );
        employeeBaseDto.setJobBeginDate( entity.getJobBeginDate() );
        employeeBaseDto.setJobEndDate( entity.getJobEndDate() );
        employeeBaseDto.setAddress( entity.getAddress() );

        return employeeBaseDto;
    }

    @Override
    public Employee toEntity(EmployeeBaseDto dto) {
        if ( dto == null ) {
            return null;
        }

        EmployeeBuilder employee = Employee.builder();

        employee.department( idToDepartment( dto.getDepartment() ) );
        employee.parent( idToParent( dto.getParent() ) );
        employee.user( idToUser( dto.getUser() ) );
        employee.salary( dto.getSalary() );
        employee.jobDescription( dto.getJobDescription() );
        employee.jobBeginDate( dto.getJobBeginDate() );
        employee.jobEndDate( dto.getJobEndDate() );
        employee.address( dto.getAddress() );

        return employee.build();
    }

    @Override
    public void update(Employee employee, EmployeeBaseDto dto) {
        if ( dto == null ) {
            return;
        }

        employee.setDepartment( idToDepartment( dto.getDepartment() ) );
        employee.setParent( idToParent( dto.getParent() ) );
        employee.setId( dto.getId() );
        employee.setModifiedAt( dto.getModifiedAt() );
        employee.setCreatedAt( dto.getCreatedAt() );
        employee.setSalary( dto.getSalary() );
        employee.setJobDescription( dto.getJobDescription() );
        employee.setJobBeginDate( dto.getJobBeginDate() );
        employee.setJobEndDate( dto.getJobEndDate() );
        employee.setAddress( dto.getAddress() );
    }

    @Override
    public List<Employee> toListEntity(List<EmployeeBaseDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<Employee> list = new ArrayList<Employee>( dto.size() );
        for ( EmployeeBaseDto employeeBaseDto : dto ) {
            list.add( toEntity( employeeBaseDto ) );
        }

        return list;
    }

    @Override
    public List<EmployeeBaseDto> entityToListDto(List<Employee> dto) {
        if ( dto == null ) {
            return null;
        }

        List<EmployeeBaseDto> list = new ArrayList<EmployeeBaseDto>( dto.size() );
        for ( Employee employee : dto ) {
            list.add( toBaseDto( employee ) );
        }

        return list;
    }

    @Override
    public EmployeeSpecDto entityToSpec(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeSpecDto employeeSpecDto = new EmployeeSpecDto();

        employeeSpecDto.setDepartment( departmentToId( employee.getDepartment() ) );
        employeeSpecDto.setParent( parentToId( employee.getParent() ) );
        employeeSpecDto.setInfo( entityToUser( employee.getUser() ) );
        employeeSpecDto.setSalary( employee.getSalary() );
        employeeSpecDto.setJobDescription( employee.getJobDescription() );
        employeeSpecDto.setJobBeginDate( employee.getJobBeginDate() );
        employeeSpecDto.setJobEndDate( employee.getJobEndDate() );
        employeeSpecDto.setAddress( employee.getAddress() );

        return employeeSpecDto;
    }
}
