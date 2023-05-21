package com.shopping.service.mapper;

import com.shopping.service.entity.OrderDetail;
import com.shopping.service.model.order_detail.OrderDetailBaseDto;
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
public class OrderDetailMapperImpl extends OrderDetailMapper {

    @Override
    public OrderDetailBaseDto toBaseDto(OrderDetail entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDetailBaseDto orderDetailBaseDto = new OrderDetailBaseDto();

        orderDetailBaseDto.setProduct( productToId( entity.getProduct() ) );
        orderDetailBaseDto.setOrder( orderToId( entity.getOrder() ) );
        orderDetailBaseDto.setId( entity.getId() );
        orderDetailBaseDto.setModifiedAt( entity.getModifiedAt() );
        orderDetailBaseDto.setCreatedAt( entity.getCreatedAt() );
        orderDetailBaseDto.setPrice( (float) entity.getPrice() );
        orderDetailBaseDto.setDiscount( entity.getDiscount() );
        orderDetailBaseDto.setQuantity( entity.getQuantity() );

        return orderDetailBaseDto;
    }

    @Override
    public OrderDetail toEntity(OrderDetailBaseDto dto) {
        if ( dto == null ) {
            return null;
        }

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setProduct( idToProduct( dto.getProduct() ) );
        orderDetail.setOrder( idToOrder( dto.getOrder() ) );
        orderDetail.setId( dto.getId() );
        orderDetail.setModifiedAt( dto.getModifiedAt() );
        orderDetail.setCreatedAt( dto.getCreatedAt() );
        if ( dto.getPrice() != null ) {
            orderDetail.setPrice( dto.getPrice() );
        }
        if ( dto.getDiscount() != null ) {
            orderDetail.setDiscount( dto.getDiscount() );
        }
        if ( dto.getQuantity() != null ) {
            orderDetail.setQuantity( dto.getQuantity() );
        }

        return orderDetail;
    }

    @Override
    public List<OrderDetailBaseDto> toBaseListDto(List<OrderDetail> entity) {
        if ( entity == null ) {
            return null;
        }

        List<OrderDetailBaseDto> list = new ArrayList<OrderDetailBaseDto>( entity.size() );
        for ( OrderDetail orderDetail : entity ) {
            list.add( toBaseDto( orderDetail ) );
        }

        return list;
    }

    @Override
    public List<OrderDetail> toListEntity(List<OrderDetailBaseDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<OrderDetail> list = new ArrayList<OrderDetail>( dtos.size() );
        for ( OrderDetailBaseDto orderDetailBaseDto : dtos ) {
            list.add( toEntity( orderDetailBaseDto ) );
        }

        return list;
    }

    @Override
    public void update(OrderDetail entity, OrderDetailBaseDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getProduct() != null ) {
            entity.setProduct( idToProduct( dto.getProduct() ) );
        }
        if ( dto.getOrder() != null ) {
            entity.setOrder( idToOrder( dto.getOrder() ) );
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
        if ( dto.getPrice() != null ) {
            entity.setPrice( dto.getPrice() );
        }
        if ( dto.getDiscount() != null ) {
            entity.setDiscount( dto.getDiscount() );
        }
        if ( dto.getQuantity() != null ) {
            entity.setQuantity( dto.getQuantity() );
        }
    }
}
