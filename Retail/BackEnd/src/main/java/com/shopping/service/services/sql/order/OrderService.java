package com.shopping.service.services.sql.order;

import com.shopping.service.model.order.OrderBaseDto;
import com.shopping.service.model.order.OrderDto;

import java.util.List;

public interface OrderService {

    OrderBaseDto findById(Long id);

    List<OrderBaseDto> getAll();

    OrderBaseDto save(OrderBaseDto dto);

    OrderBaseDto update(Long id, OrderBaseDto dto);

    OrderBaseDto saveJoin(OrderDto dto);

}
