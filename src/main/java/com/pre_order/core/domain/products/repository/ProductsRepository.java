package com.pre_order.core.domain.products.repository;

import com.pre_order.core.domain.products.dto.ProductResponseDto;
import com.pre_order.core.domain.products.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    Page<ProductResponseDto> findAllByIsShow(Pageable pageable, Boolean isShow);
    Optional<Products> findByIdAndIsShow(Long id, Boolean isShow);
}
