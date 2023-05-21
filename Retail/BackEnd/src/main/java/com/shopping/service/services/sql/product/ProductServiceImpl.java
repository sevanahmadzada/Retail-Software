package com.shopping.service.services.sql.product;

import com.shopping.service.entity.Product;
import com.shopping.service.mapper.ProductMapper;
import com.shopping.service.model.PaginationResponse;
import com.shopping.service.model.product.ProductBaseDto;
import com.shopping.service.model.product.ProductReportProj;
import com.shopping.service.repository.sql.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    @Override
    public ProductBaseDto findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NullPointerException("Product not find"));
        return mapper.toBaseDto(product);
    }

    @Override
    public PaginationResponse<ProductBaseDto> getAll(int page, int size, String sorting, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sorting).ascending() :
                Sort.by(sorting).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = repository.findAllByStatus('1', pageable);
        List<Product> content = products.getContent();

        PaginationResponse<ProductBaseDto> response = new PaginationResponse<>();
        response.setContent(mapper.toListBaseDto(content));
        response.setPageNumber(products.getNumber());
        response.setPageSize(products.getSize());
        response.setTotalElements(products.getTotalElements());
        response.setTotalPages(products.getTotalPages());
        response.setLast(products.isLast());
        return response;
    }

    @Override
    public ProductBaseDto save(ProductBaseDto dto) {
        Product product = mapper.toEntity(dto);
        repository.save(product);
        return mapper.toBaseDto(product);
    }

    @Override
    public ProductBaseDto update(Long id, ProductBaseDto dto) {
        Product product = repository.findById(id).orElseThrow(() -> new NullPointerException("Product not find"));
        mapper.update(product, dto);
        repository.save(product);
        return mapper.toBaseDto(product);
    }

    @Override
    public ProductBaseDto delete(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NullPointerException("Product not find"));
        ProductBaseDto result = mapper.toBaseDto(product);
        repository.delete(product);
        return result;
    }

    @Override
    public List<ProductReportProj> report() {
        return repository.getTop10Products();
    }

    @Override
    public double totalSumOfProducts() {
        return Math.round(repository.getTotalSumOfProducts()*10.0)/10.0;
    }

    @Override
    public List<ProductBaseDto> search(String name) {
        return mapper.toListBaseDto(repository.findAllByNameLike(name));
    }
}
