package com.shopping.service.services.sql.transaction;

import com.shopping.service.entity.Customer;
import com.shopping.service.entity.Order;
import com.shopping.service.entity.Transaction;
import com.shopping.service.mapper.TransactionMapper;
import com.shopping.service.model.customer.CustomerProj;
import com.shopping.service.model.order.OrderBaseDto;
import com.shopping.service.model.transaction.TransactionBaseDto;
import com.shopping.service.repository.sql.TransactionRepository;
import com.shopping.service.services.sql.customer.CustomerService;
import com.shopping.service.services.sql.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    private final TransactionMapper mapper;

    private final OrderService orderService;

    private final CustomerService customerService;

    @Override
    public TransactionBaseDto findById(Long id) {
        Transaction transaction = repository.findById(id).orElseThrow(() -> new NullPointerException("Transaction not found!!!"));
        return mapper.toBaseDto(transaction);
    }

    @Override
    public List<TransactionBaseDto> getAll() {
        List<Transaction> transactions = repository.findAll();
        return mapper.toBaseListDto(transactions);
    }

    @Override
    public TransactionBaseDto save(TransactionBaseDto dto) {
        CustomerProj proj = customerService.getCustomersLikeUserName("guest").get(0);
        Transaction transaction = new Transaction();
        OrderBaseDto order = orderService.findById(dto.getOrder());
//        mapper.update(transaction, dto);
        transaction.setDate(LocalDateTime.now());
        transaction.setCustomer(order.getCustomer() != null ? new Customer(order.getCustomer()) : new Customer(proj.getId()));
        transaction.setTotal(order.getTotal());
        transaction.setPaymentType(dto.getPaymentType());
        transaction.setOrder(new Order(order.getId()));
        transaction.setStatus('1');
        repository.save(transaction);
        return mapper.toBaseDto(transaction);
    }

    @Override
    public TransactionBaseDto update(Long id, TransactionBaseDto dto) {
        Transaction transaction = repository.findById(id).orElseThrow(() -> new NullPointerException("Transaction not found!!!"));
        mapper.update(transaction, dto);
        repository.save(transaction);
        return mapper.toBaseDto(transaction);
    }

    @Override
    public double monthlyTurnover() {
        return Math.round(repository.getSumOfTransactions() * 10.0) / 10.0;
    }
}
