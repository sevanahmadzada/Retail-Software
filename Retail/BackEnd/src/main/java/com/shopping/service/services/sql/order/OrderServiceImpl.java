package com.shopping.service.services.sql.order;

import com.shopping.service.entity.Order;
import com.shopping.service.entity.OrderDetail;
import com.shopping.service.entity.Product;
import com.shopping.service.mapper.OrderDetailMapper;
import com.shopping.service.mapper.OrderMapper;
import com.shopping.service.model.order.OrderBaseDto;
import com.shopping.service.model.order.OrderDto;
import com.shopping.service.repository.sql.OrderRepository;
import com.shopping.service.repository.sql.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    private final OrderMapper mapper;

    private final OrderDetailMapper orderDetailMapper;

    private final ProductRepository productRepository;

    @Override
    public OrderBaseDto findById(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new NullPointerException("Order not found!!!"));
        return mapper.toBaseDto(order);
    }

    @Override
    public List<OrderBaseDto> getAll() {
        List<Order> orders = repository.findAll();
        return mapper.toBaseListDto(orders);
    }

    @Override
    public OrderBaseDto save(OrderBaseDto dto) {
        Order order = new Order();
        mapper.update(order, dto);
        repository.save(order);
        return mapper.toBaseDto(order);
    }

    @Override
    public OrderBaseDto update(Long id, OrderBaseDto dto) {
        Order order = repository.findById(id).orElseThrow(() -> new NullPointerException("Order not found!!!"));
        mapper.update(order, dto);
        return mapper.toBaseDto(order);
    }

    @Transactional
    @Override
    public OrderBaseDto saveJoin(OrderDto dto) {
        Order order = mapper.simpleDtoToEntity(dto);
        List<OrderDetail> details = dto.getOrderDetailDto().stream().map(arg -> {
            Product product = productRepository.findById(arg.getProduct()).orElseThrow();

            if (product.getQuantity() > 0 || product.getQuantity() >= arg.getQuantity()) {

                if (arg.getQuantity() > product.getQuantity())
                    arg.setQuantity(product.getQuantity());

                OrderDetail detail = orderDetailMapper.toEntity(arg);
                product.setQuantity(product.getQuantity() - arg.getQuantity());
                detail.setOrder(order);
                detail.setPrice(product.getPrice());
                detail.setDiscount(0f);
                return detail;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        if (details.isEmpty())
            throw new NullPointerException("There is no detail to do order!!!");

        Double sumOfDetails = details.stream().reduce(0d, (arg, arg2) -> arg + (arg2.getQuantity() * arg2.getPrice()), Double::sum);
        order.setTotal(Math.round(sumOfDetails * 10.0) / 10.0);
        order.setOrderDetails(details);
        repository.save(order);
        return mapper.toBaseDto(order);
    }

}
