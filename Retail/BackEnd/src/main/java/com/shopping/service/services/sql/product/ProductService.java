package com.shopping.service.services.sql.product;

import com.shopping.service.model.PaginationResponse;
import com.shopping.service.model.product.ProductBaseDto;
import com.shopping.service.model.product.ProductReportProj;

import java.util.List;

public interface ProductService {

    ProductBaseDto findById(Long id);

    PaginationResponse<ProductBaseDto> getAll(int page, int size, String sortBy, String sortDir);

    ProductBaseDto save(ProductBaseDto dto);

    ProductBaseDto update(Long id, ProductBaseDto dto);

    ProductBaseDto delete(Long id);

    List<ProductReportProj> report();

    double totalSumOfProducts();

    List<ProductBaseDto> search(String name);

}
