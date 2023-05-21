package com.shopping.service.mapper;

import com.shopping.service.entity.Customer;
import com.shopping.service.entity.Order;
import com.shopping.service.entity.Transaction;
import com.shopping.service.model.transaction.TransactionBaseDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {

    @Mapping(target = "customer", qualifiedByName = "customerToId")
    @Mapping(target = "order", qualifiedByName = "orderToId")
    public abstract TransactionBaseDto toBaseDto(Transaction entity);

    @Mapping(target = "customer", qualifiedByName = "idToCustomer")
    @Mapping(target = "order", qualifiedByName = "idToOrder")
    public abstract Transaction toEntity(TransactionBaseDto dto);

    public abstract List<TransactionBaseDto> toBaseListDto(List<Transaction> entities);

    public abstract List<Transaction> toListEntity(List<TransactionBaseDto> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customer", qualifiedByName = "idToCustomer")
    @Mapping(target = "order", qualifiedByName = "idToOrder")
    public abstract void update(@MappingTarget Transaction entity, TransactionBaseDto dto);

    @Named("orderToId")
    Long orderToId(Order order) {
        return order != null ? order.getId() : null;
    }

    @Named("idToOrder")
    Order idToOrder(Long id) {
        return id != null ? new Order(id) : null;
    }

    @Named("customerToId")
    Long customerToId(Customer customer) {
        return customer != null ? customer.getId() : null;
    }

    @Named("idToCustomer")
    Customer idToCustomer(Long id) {
        return id != null ? new Customer(id) : null;
    }

}
