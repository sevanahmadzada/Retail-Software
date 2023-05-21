package com.shopping.service.mapper;

import com.shopping.service.entity.Department;
import com.shopping.service.entity.Employee;
import com.shopping.service.entity.Role;
import com.shopping.service.entity.User;
import com.shopping.service.model.employee.EmployeeBaseDto;
import com.shopping.service.model.employee.EmployeeSpecDto;
import com.shopping.service.model.employee.UserDto;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {

    @Named("toBaseDto")
    @Mapping(target = "department", qualifiedByName = "departmentToId")
    @Mapping(target = "parent", qualifiedByName = "parentToId")
    @Mapping(target = "user", qualifiedByName = "userToId")
    public abstract EmployeeBaseDto toBaseDto(Employee entity);

    @Mapping(target = "department", qualifiedByName = "idToDepartment")
    @Mapping(target = "parent", qualifiedByName = "idToParent")
    @Mapping(target = "user", qualifiedByName = "idToUser")
    public abstract Employee toEntity(EmployeeBaseDto dto);

    @Mapping(target = "department", qualifiedByName = "idToDepartment")
    @Mapping(target = "parent", qualifiedByName = "idToParent")
    @Mapping(target = "user", ignore = true /*qualifiedByName = "idToUser"*/)
    public abstract void update(@MappingTarget Employee employee, EmployeeBaseDto dto);

    public abstract List<Employee> toListEntity(List<EmployeeBaseDto> dto);

    @IterableMapping(qualifiedByName = "toBaseDto")
    public abstract List<EmployeeBaseDto> entityToListDto(List<Employee> dto);

    @Mapping(target = "department", qualifiedByName = "departmentToId")
    @Mapping(target = "parent", qualifiedByName = "parentToId")
    @Mapping(target = "info", source = "user",qualifiedByName = "entityToUser")
    public abstract EmployeeSpecDto entityToSpec(Employee employee);

    @Named("entityToUser")
    UserDto entityToUser(User user){
        UserDto res = new UserDto();
        List<Role> roles = user.getRoles();
        res.setUsername(user.getUsername());
        res.setRollName(roles.get(0).getRollName());
        return res;
    }

    @Named("userToId")
    Long userToId(User user) {
        return user != null ? user.getId() : null;
    }

    @Named("idToUser")
    User idToUser(Long id) {
        return id != null ? new User(id) : null;
    }

    @Named("departmentToId")
    Long departmentToId(Department department) {
        return department != null ? department.getId() : null;
    }

    @Named("idToDepartment")
    Department idToDepartment(Long id) {
        return id != null ? new Department(id) : null;
    }

    @Named("parentToId")
    Long parentToId(Employee employee) {
        return employee != null ? employee.getId() : null;
    }

    @Named("idToParent")
    Employee idToParent(Long id) {
        return id != null ? new Employee(id) : null;
    }


}
