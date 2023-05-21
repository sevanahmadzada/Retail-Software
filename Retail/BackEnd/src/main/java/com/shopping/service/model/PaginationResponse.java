package com.shopping.service.model;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResponse<T> {

    private List<T> content;

    private int pageNumber;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;

}
