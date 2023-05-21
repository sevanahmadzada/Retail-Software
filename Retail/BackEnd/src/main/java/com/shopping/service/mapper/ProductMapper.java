package com.shopping.service.mapper;

import com.shopping.service.entity.Employee;
import com.shopping.service.entity.Product;
import com.shopping.service.model.product.ProductBaseDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Mapping(target = "addedBy", qualifiedByName = "entityToLong")
    public abstract ProductBaseDto toBaseDto(Product entity);

    @Mapping(target = "addedBy", qualifiedByName = "idToEmpEntity")
    public abstract Product toEntity(ProductBaseDto dto);

    public abstract List<ProductBaseDto> toListBaseDto(List<Product> entities);

    public abstract List<Product> toListEntity(List<ProductBaseDto> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "addedBy", qualifiedByName = "idToEmpEntity")
    public abstract Product update(@MappingTarget Product entity, ProductBaseDto dto);

    @Named("idToEmpEntity")
    Employee idToEmpEntity(Long id) {
        return id != null ? new Employee(id) : null;
    }

    @Named("entityToLong")
    Long entityToLong(Employee entity) {
        return entity != null ? entity.getId() : null;
    }

}
