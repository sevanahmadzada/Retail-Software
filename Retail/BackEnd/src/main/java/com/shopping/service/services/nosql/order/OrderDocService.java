package com.shopping.service.services.nosql.order;

import com.shopping.service.document.OrderDetailDocument;
import com.shopping.service.document.OrderDocument;
import com.shopping.service.document.ProductDocument;
import com.shopping.service.repository.nosql.OrderDetailsDocRepository;
import com.shopping.service.repository.nosql.OrderDocRepository;
import com.shopping.service.repository.nosql.SequenceRepository;
import com.shopping.service.services.nosql.product.ProductDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderDocService {

    private final OrderDocRepository repository;
    private final OrderDetailsDocRepository orderDetailsRepository;
    private final ProductDocService productService;
    private final OrderDocMapper mapper;
    private final SequenceRepository sequence;
    private final String SEQ_NAME = "order_seq";
    private final String DETAIL_SEQ = "order_detail_seq";

    public OrderDocument findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NullPointerException("Product not find"));
    }

    public List<OrderDocument> getAll() {
        return repository.findAll();
    }

    public OrderDocument doOrder(OrderDocument document) {
        document.setId(sequence.getNextSequenceId(SEQ_NAME, "order_tbl"));
        document.setCreatedAt(LocalDateTime.now());
        List<OrderDetailDocument> details = document.getOrderDetails().stream().map(arg -> {
            ProductDocument product = productService.findById(arg.getProduct());
            if (product == null)
                throw new NullPointerException("There is no Product with this id!!!");

            if (product.getQuantity() > 0 || product.getQuantity() >= arg.getQuantity()) {
                if (arg.getQuantity() > product.getQuantity())
                    arg.setQuantity(product.getQuantity());

                arg.setOrder(document.getId());
                arg.setId(sequence.getNextSequenceId(DETAIL_SEQ, "order_detail"));
                arg.setCreatedAt(LocalDateTime.now());
                arg.setPrice(product.getPrice());
                arg.setDiscount(0f);
                product.setQuantity(product.getQuantity() - arg.getQuantity());
                productService.update(product.getId(), product);
                return arg;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        if (details.isEmpty())
            throw new NullPointerException("There is no detail to do order!!!");

        Double sumOfDetails = details.stream()
                .reduce(0d, (arg, arg2) -> arg + (arg2.getQuantity() * arg2.getPrice()), Double::sum);
        document.setTotal(sumOfDetails);
        document.setOrderDetails(details);
        orderDetailsRepository.saveAll(document.getOrderDetails());
        return repository.save(document);
    }

    public OrderDocument update(Long id, OrderDocument document) {
        OrderDocument org = repository.findById(id).orElseThrow(() -> new NullPointerException("Product not find"));
        org.setUpdatedAt(LocalDateTime.now());
        mapper.update(org, document);
        return repository.save(org);
    }

}
