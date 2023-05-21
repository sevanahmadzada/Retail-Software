package com.shopping.service.services.sql.transaction;

import com.shopping.service.model.transaction.TransactionBaseDto;

import java.util.List;

public interface TransactionService {

    TransactionBaseDto findById(Long id);

    List<TransactionBaseDto> getAll();

    TransactionBaseDto save(TransactionBaseDto dto);

    TransactionBaseDto update(Long id, TransactionBaseDto dto);

    double monthlyTurnover();

}
