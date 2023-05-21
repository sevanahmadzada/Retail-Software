package com.shopping.service.mapper;

import com.shopping.service.entity.Order;
import com.shopping.service.model.order.OrderBaseDto;
import com.shopping.service.model.order.OrderDto;
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
public class OrderMapperImpl extends OrderMapper {

    @Override
    public Order simpleDtoToEntity(OrderDto dto) {
        if ( dto == null ) {
            return null;
        }

        Order order = new Order();

        order.setCustomer( idToCustomer( dto.getCustomer() ) );
        order.setEmployee( idToEmployee( dto.getEmployee() ) );
        order.setId( dto.getId() );
        order.setModifiedAt( dto.getModifiedAt() );
        order.setCreatedAt( dto.getCreatedAt() );
        order.setContent( dto.getContent() );

        return order;
    }

    @Override
    public OrderBaseDto toBaseDto(Order entity) {
        if ( entity == null ) {
            return null;
        }

        OrderBaseDto orderBaseDto = new OrderBaseDto();

        orderBaseDto.setCustomer( customerToId( entity.getCustomer() ) );
        orderBaseDto.setEmployee( employeeToId( entity.getEmployee() ) );
        orderBaseDto.setId( entity.getId() );
        orderBaseDto.setModifiedAt( entity.getModifiedAt() );
        orderBaseDto.setCreatedAt( entity.getCreatedAt() );
        orderBaseDto.setDiscount( entity.getDiscount() );
        orderBaseDto.setTotal( entity.getTotal() );
        orderBaseDto.setContent( entity.getContent() );

        return orderBaseDto;
    }

    @Override
    public Order toEntity(OrderBaseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Order order = new Order();

        order.setCustomer( idToCustomer( dto.getCustomer() ) );
        order.setEmployee( idToEmployee( dto.getEmployee() ) );
        order.setId( dto.getId() );
        order.setModifiedAt( dto.getModifiedAt() );
        order.setCreatedAt( dto.getCreatedAt() );
        order.setDiscount( dto.getDiscount() );
        order.setTotal( dto.getTotal() );
        order.setContent( dto.getContent() );

        return order;
    }

    @Override
    public List<OrderBaseDto> toBaseListDto(List<Order> entity) {
        if ( entity == null ) {
            return null;
        }

        List<OrderBaseDto> list = new ArrayList<OrderBaseDto>( entity.size() );
        for ( Order order : entity ) {
            list.add( toBaseDto( order ) );
        }

        return list;
    }

    @Override
    public List<Order> toListEntity(List<OrderBaseDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Order> list = new ArrayList<Order>( dtos.size() );
        for ( OrderBaseDto orderBaseDto : dtos ) {
            list.add( toEntity( orderBaseDto ) );
        }

        return list;
    }

    @Override
    public void update(Order entity, OrderBaseDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCustomer() != null ) {
            entity.setCustomer( idToCustomer( dto.getCustomer() ) );
        }
        if ( dto.getEmployee() != null ) {
            entity.setEmployee( idToEmployee( dto.getEmployee() ) );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getModifiedAt() != null ) {
            entity.setModifiedAt( dto.getModifiedAt() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getDiscount() != null ) {
            entity.setDiscount( dto.getDiscount() );
        }
        if ( dto.getTotal() != null ) {
            entity.setTotal( dto.getTotal() );
        }
        if ( dto.getContent() != null ) {
            entity.setContent( dto.getContent() );
        }
    }
}
