package com.shopping.service.mapper;

import com.shopping.service.entity.Product;
import com.shopping.service.model.product.ProductBaseDto;
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
public class ProductMapperImpl extends ProductMapper {

    @Override
    public ProductBaseDto toBaseDto(Product entity) {
        if ( entity == null ) {
            return null;
        }

        ProductBaseDto productBaseDto = new ProductBaseDto();

        productBaseDto.setAddedBy( entityToLong( entity.getAddedBy() ) );
        productBaseDto.setId( entity.getId() );
        productBaseDto.setModifiedAt( entity.getModifiedAt() );
        productBaseDto.setCreatedAt( entity.getCreatedAt() );
        productBaseDto.setDescription( entity.getDescription() );
        productBaseDto.setName( entity.getName() );
        productBaseDto.setPrice( entity.getPrice() );
        productBaseDto.setQuantity( entity.getQuantity() );
        productBaseDto.setDiscount( entity.getDiscount() );

        return productBaseDto;
    }

    @Override
    public Product toEntity(ProductBaseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setAddedBy( idToEmpEntity( dto.getAddedBy() ) );
        product.setId( dto.getId() );
        product.setModifiedAt( dto.getModifiedAt() );
        product.setCreatedAt( dto.getCreatedAt() );
        product.setDescription( dto.getDescription() );
        product.setName( dto.getName() );
        product.setPrice( dto.getPrice() );
        product.setQuantity( dto.getQuantity() );
        product.setDiscount( dto.getDiscount() );

        return product;
    }

    @Override
    public List<ProductBaseDto> toListBaseDto(List<Product> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductBaseDto> list = new ArrayList<ProductBaseDto>( entities.size() );
        for ( Product product : entities ) {
            list.add( toBaseDto( product ) );
        }

        return list;
    }

    @Override
    public List<Product> toListEntity(List<ProductBaseDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( dtos.size() );
        for ( ProductBaseDto productBaseDto : dtos ) {
            list.add( toEntity( productBaseDto ) );
        }

        return list;
    }

    @Override
    public Product update(Product entity, ProductBaseDto dto) {
        if ( dto == null ) {
            return null;
        }

        if ( dto.getAddedBy() != null ) {
            entity.setAddedBy( idToEmpEntity( dto.getAddedBy() ) );
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
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        entity.setPrice( dto.getPrice() );
        if ( dto.getQuantity() != null ) {
            entity.setQuantity( dto.getQuantity() );
        }
        if ( dto.getDiscount() != null ) {
            entity.setDiscount( dto.getDiscount() );
        }

        return entity;
    }
}
