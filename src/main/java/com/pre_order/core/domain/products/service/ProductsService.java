package com.pre_order.core.domain.products.service;

import com.pre_order.core.domain.products.dto.ProductResponseDto;
import com.pre_order.core.domain.products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    public List<ProductResponseDto> getProductList(int page) {
        page--;
        Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "id");

        return productsRepository.findAllByIsShow(pageable, true).getContent();
    }

}
