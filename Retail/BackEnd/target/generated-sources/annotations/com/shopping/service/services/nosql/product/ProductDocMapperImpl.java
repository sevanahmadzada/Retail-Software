package com.shopping.service.services.nosql.product;

import com.shopping.service.document.ProductDocument;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-03T07:58:26+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class ProductDocMapperImpl extends ProductDocMapper {

    @Override
    public void update(ProductDocument document, ProductDocument doc) {
        if ( doc == null ) {
            return;
        }

        if ( doc.getId() != null ) {
            document.setId( doc.getId() );
        }
        if ( doc.getCreatedAt() != null ) {
            document.setCreatedAt( doc.getCreatedAt() );
        }
        if ( doc.getModifiedAt() != null ) {
            document.setModifiedAt( doc.getModifiedAt() );
        }
        if ( doc.getDescription() != null ) {
            document.setDescription( doc.getDescription() );
        }
        if ( doc.getStatus() != null ) {
            document.setStatus( doc.getStatus() );
        }
        if ( doc.getName() != null ) {
            document.setName( doc.getName() );
        }
        document.setPrice( doc.getPrice() );
        if ( doc.getQuantity() != null ) {
            document.setQuantity( doc.getQuantity() );
        }
        if ( doc.getDiscount() != null ) {
            document.setDiscount( doc.getDiscount() );
        }
        if ( doc.getAddedBy() != null ) {
            document.setAddedBy( doc.getAddedBy() );
        }
    }
}
