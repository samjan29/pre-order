package com.pre_order.core.domain.products.service;

import com.pre_order.core.domain.orders.dto.WishListRequestDto;
import com.pre_order.core.domain.products.dto.ProductResponseDto;
import com.pre_order.core.domain.products.entity.Products;
import com.pre_order.core.domain.products.repository.ProductsRepository;
import com.pre_order.core.global.exception.CustomException;
import com.pre_order.core.global.exception.ErrorCode;
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

    public Products checkProduct(WishListRequestDto wishListRequestDto) {
        Products product = productsRepository.findByIdAndIsShow(wishListRequestDto.productId(), true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        if (product.getStock() < wishListRequestDto.quantity()) {
//            throw new CustomException(ErrorCode.NOT_ENOUGH_STOCK);
        }
        return product;
    }

    @Transactional
    public void updateStock(WishListRequestDto wishListRequestDto, Products product) {
        product.updateStock(product.getStock() - wishListRequestDto.quantity());
        productsRepository.save(product);
    }
}
