package com.shopping.service.mapper;

import com.shopping.service.entity.Order;
import com.shopping.service.entity.OrderDetail;
import com.shopping.service.entity.Product;
import com.shopping.service.model.order_detail.OrderDetailBaseDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderDetailMapper {

    @Mapping(target = "product", qualifiedByName = "productToId")
    @Mapping(target = "order", qualifiedByName = "orderToId")
    public abstract OrderDetailBaseDto toBaseDto(OrderDetail entity);

    @Mapping(target = "product", qualifiedByName = "idToProduct")
    @Mapping(target = "order", qualifiedByName = "idToOrder")
    public abstract OrderDetail toEntity(OrderDetailBaseDto dto);

    public abstract List<OrderDetailBaseDto> toBaseListDto(List<OrderDetail> entity);

    public abstract List<OrderDetail> toListEntity(List<OrderDetailBaseDto> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "product", qualifiedByName = "idToProduct")
    @Mapping(target = "order", qualifiedByName = "idToOrder")
    public abstract void update(@MappingTarget OrderDetail entity, OrderDetailBaseDto dto);

    @Named("productToId")
    Long productToId(Product product) {
        return product != null ? product.getId() : null;
    }

    @Named("idToProduct")
    Product idToProduct(Long id) {
        return id != null ? new Product(id) : null;
    }

    @Named("orderToId")
    Long orderToId(Order order) {
        return order != null ? order.getId() : null;
    }

    @Named("idToOrder")
    Order idToOrder(Long id) {
        return id != null ? new Order(id) : null;
    }

}
