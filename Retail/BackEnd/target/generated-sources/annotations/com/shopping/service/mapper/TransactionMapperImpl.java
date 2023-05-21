package com.shopping.service.mapper;

import com.shopping.service.entity.Transaction;
import com.shopping.service.model.transaction.TransactionBaseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-03T07:58:26+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class TransactionMapperImpl extends TransactionMapper {

    @Override
    public TransactionBaseDto toBaseDto(Transaction entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionBaseDto transactionBaseDto = new TransactionBaseDto();

        transactionBaseDto.setCustomer( customerToId( entity.getCustomer() ) );
        transactionBaseDto.setOrder( orderToId( entity.getOrder() ) );
        transactionBaseDto.setId( entity.getId() );
        transactionBaseDto.setModifiedAt( entity.getModifiedAt() );
        transactionBaseDto.setCreatedAt( entity.getCreatedAt() );
        transactionBaseDto.setPaymentType( entity.getPaymentType() );
        transactionBaseDto.setTotal( entity.getTotal() );
        transactionBaseDto.setDate( entity.getDate() );

        return transactionBaseDto;
    }

    @Override
    public Transaction toEntity(TransactionBaseDto dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setCustomer( idToCustomer( dto.getCustomer() ) );
        transaction.setOrder( idToOrder( dto.getOrder() ) );
        transaction.setId( dto.getId() );
        transaction.setModifiedAt( dto.getModifiedAt() );
        transaction.setCreatedAt( dto.getCreatedAt() );
        transaction.setPaymentType( dto.getPaymentType() );
        transaction.setDate( dto.getDate() );
        transaction.setTotal( dto.getTotal() );

        return transaction;
    }

    @Override
    public List<TransactionBaseDto> toBaseListDto(List<Transaction> entities) {
        if ( entities == null ) {
            return null;
        }

        List<TransactionBaseDto> list = new ArrayList<TransactionBaseDto>( entities.size() );
        for ( Transaction transaction : entities ) {
            list.add( toBaseDto( transaction ) );
        }

        return list;
    }

    @Override
    public List<Transaction> toListEntity(List<TransactionBaseDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Transaction> list = new ArrayList<Transaction>( dtos.size() );
        for ( TransactionBaseDto transactionBaseDto : dtos ) {
            list.add( toEntity( transactionBaseDto ) );
        }

        return list;
    }

    @Override
    public void update(Transaction entity, TransactionBaseDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCustomer() != null ) {
            entity.setCustomer( idToCustomer( dto.getCustomer() ) );
        }
        if ( dto.getOrder() != null ) {
            entity.setOrder( idToOrder( dto.getOrder() ) );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getModifiedAt() != null ) {
            entity.setModifiedAt( dto.getModifiedAt() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getPaymentType() != null ) {
            entity.setPaymentType( dto.getPaymentType() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
        }
        if ( dto.getTotal() != null ) {
            entity.setTotal( dto.getTotal() );
        }
    }
}
