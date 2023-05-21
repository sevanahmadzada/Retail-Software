package com.shopping.service.services.nosql.transaction;

import com.shopping.service.document.OrderDocument;
import com.shopping.service.document.TransactionDocument;
import com.shopping.service.document.UserDocument;
import com.shopping.service.model.transaction.InsertTransactionDto;
import com.shopping.service.repository.nosql.*;
import com.shopping.service.services.nosql.order.OrderDocService;
import com.shopping.service.services.nosql.user.UserDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionDocService {

    private final TransactionDocRepository transactionDocRepository;
    private final UserDocumentService userDocumentService;
    private final OrderDocService orderDocService;
    private final SequenceRepository sequenceRepository;
    private final String SEQ_NAME = "transaction_seq";

    public TransactionDocument save(InsertTransactionDto dto){
        UserDocument userDocument = userDocumentService.getByNameLike("guest").get(0);
        OrderDocument orderDocument = orderDocService.findById(dto.getOrder());

        TransactionDocument transactionDocument = new TransactionDocument();

        transactionDocument.setId(sequenceRepository.getNextSequenceId(SEQ_NAME, "transaction"));
        transactionDocument.setCreatedAt(LocalDateTime.now());
        transactionDocument.setUpdatedAt(LocalDateTime.now());

        transactionDocument.setDate(LocalDateTime.now());
        transactionDocument.setCustomer(orderDocument.getCustomer() != null ? orderDocument.getCustomer() : userDocument.getId());
        transactionDocument.setTotal(Math.round(orderDocument.getTotal()*10.0)/10.0);
        transactionDocument.setPaymentType(dto.getPaymentType());
        transactionDocument.setOrder(dto.getOrder());
        transactionDocument.setStatus('1');

        transactionDocRepository.save(transactionDocument);


        return transactionDocument;
    }

    public double getTotal(LocalDateTime localDateTime){
        return transactionDocRepository.getTotal(localDateTime);
    }

}
