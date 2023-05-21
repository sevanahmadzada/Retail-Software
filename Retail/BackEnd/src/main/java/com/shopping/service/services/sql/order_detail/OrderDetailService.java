package com.shopping.service.services.sql.order_detail;

import com.shopping.service.model.order_detail.OrderDetailBaseDto;

import java.util.List;

public interface OrderDetailService {

    OrderDetailBaseDto findById(Long id);

    List<OrderDetailBaseDto> getAll();

    OrderDetailBaseDto save(OrderDetailBaseDto dto);

    OrderDetailBaseDto update(Long id, OrderDetailBaseDto dto);

}
