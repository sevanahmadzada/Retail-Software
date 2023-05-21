package com.shopping.service.services.nosql.order;

import com.shopping.service.document.OrderDocument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class OrderDocMapper {

    public abstract void update(@MappingTarget OrderDocument document, OrderDocument doc);

}
