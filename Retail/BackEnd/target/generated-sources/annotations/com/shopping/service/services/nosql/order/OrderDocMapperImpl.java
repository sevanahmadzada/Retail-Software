package com.shopping.service.services.nosql.order;

import com.shopping.service.document.OrderDetailDocument;
import com.shopping.service.document.OrderDocument;
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
public class OrderDocMapperImpl extends OrderDocMapper {

    @Override
    public void update(OrderDocument document, OrderDocument doc) {
        if ( doc == null ) {
            return;
        }

        document.setId( doc.getId() );
        document.setCreatedAt( doc.getCreatedAt() );
        document.setUpdatedAt( doc.getUpdatedAt() );
        document.setCustomer( doc.getCustomer() );
        document.setEmployee( doc.getEmployee() );
        document.setDiscount( doc.getDiscount() );
        document.setTotal( doc.getTotal() );
        document.setContent( doc.getContent() );
        if ( document.getOrderDetails() != null ) {
            List<OrderDetailDocument> list = doc.getOrderDetails();
            if ( list != null ) {
                document.getOrderDetails().clear();
                document.getOrderDetails().addAll( list );
            }
            else {
                document.setOrderDetails( null );
            }
        }
        else {
            List<OrderDetailDocument> list = doc.getOrderDetails();
            if ( list != null ) {
                document.setOrderDetails( new ArrayList<OrderDetailDocument>( list ) );
            }
        }
    }
}
