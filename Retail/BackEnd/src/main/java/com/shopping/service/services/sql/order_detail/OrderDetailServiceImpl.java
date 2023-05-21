package com.shopping.service.services.sql.order_detail;

import com.shopping.service.entity.OrderDetail;
import com.shopping.service.mapper.OrderDetailMapper;
import com.shopping.service.model.order_detail.OrderDetailBaseDto;
import com.shopping.service.repository.sql.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository repository;

    private final OrderDetailMapper mapper;

    @Override
    public OrderDetailBaseDto findById(Long id) {
        OrderDetail orderDetail = repository.findById(id).orElseThrow(()->new NullPointerException("OrderDetailNotFound!!!"));
        return mapper.toBaseDto(orderDetail);
    }

    @Override
    public List<OrderDetailBaseDto> getAll() {
        List<OrderDetail> orderDetails = repository.findAll();
        return mapper.toBaseListDto(orderDetails);
    }

    @Override
    public OrderDetailBaseDto save(OrderDetailBaseDto dto) {
        OrderDetail orderDetail = new OrderDetail();
        mapper.update(orderDetail, dto);
        repository.save(orderDetail);
        return mapper.toBaseDto(orderDetail);
    }

    @Override
    public OrderDetailBaseDto update(Long id, OrderDetailBaseDto dto) {
        OrderDetail orderDetail = repository.findById(id).orElseThrow(()->new NullPointerException("OrderDetailNotFound!!!"));
        mapper.update(orderDetail, dto);
        repository.save(orderDetail);
        return mapper.toBaseDto(orderDetail);
    }
}
