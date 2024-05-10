package com.pre_order.product_service.domain.products.service;

import com.pre_order.product_service.domain.products.dto.ProductInfoResponseDto;
import com.pre_order.product_service.domain.products.dto.ProductResponseDto;
import com.pre_order.product_service.domain.products.entity.Products;
import com.pre_order.product_service.domain.products.repository.ProductsRepository;
import com.pre_order.product_service.global.exception.CustomException;
import com.pre_order.product_service.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ProductResponseDto getProduct(Long productId) {
        Products product = productsRepository.findByIdAndIsShow(productId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return new ProductResponseDto(product);
    }

    public ProductInfoResponseDto checkProduct(Long productId) {
        Products products = productsRepository.findByIdAndIsShow(productId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return new ProductInfoResponseDto(products.getStock(), products.getPrice());
    }

    @Transactional
    public void updateStock(Long productId, Integer quantity) {
        productsRepository.updateStockById(productId, quantity);
    }
}
