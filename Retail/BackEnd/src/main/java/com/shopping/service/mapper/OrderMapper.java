package com.shopping.service.mapper;

import com.shopping.service.entity.Customer;
import com.shopping.service.entity.Employee;
import com.shopping.service.entity.Order;
import com.shopping.service.model.order.OrderBaseDto;
import com.shopping.service.model.order.OrderDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Mapping(target = "customer", qualifiedByName = "idToCustomer")
    @Mapping(target = "employee", qualifiedByName = "idToEmployee")
    public abstract Order simpleDtoToEntity(OrderDto dto);

    @Mapping(target = "customer", qualifiedByName = "customerToId")
    @Mapping(target = "employee", qualifiedByName = "employeeToId")
    public abstract OrderBaseDto toBaseDto(Order entity);

    @Mapping(target = "customer", qualifiedByName = "idToCustomer")
    @Mapping(target = "employee", qualifiedByName = "idToEmployee")
    public abstract Order toEntity(OrderBaseDto dto);

    public abstract List<OrderBaseDto> toBaseListDto(List<Order> entity);

    public abstract List<Order> toListEntity(List<OrderBaseDto> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customer", qualifiedByName = "idToCustomer")
    @Mapping(target = "employee", qualifiedByName = "idToEmployee")
    public abstract void update(@MappingTarget Order entity, OrderBaseDto dto);

    @Named("customerToId")
    Long customerToId(Customer customer) {
        return customer != null ? customer.getId() : null;
    }

    @Named("idToCustomer")
    Customer idToCustomer(Long id) {
        return id != null ? new Customer(id) : null;
    }

    @Named("employeeToId")
    Long employeeToId(Employee employee) {
        return employee != null ? employee.getId() : null;
    }

    @Named("idToEmployee")
    Employee idToEmployee(Long id) {
        return id != null ? new Employee(id) : null;
    }

}
