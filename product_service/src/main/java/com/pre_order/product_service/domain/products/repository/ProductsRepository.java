package com.pre_order.product_service.domain.products.repository;

import com.pre_order.product_service.domain.products.dto.ProductResponseDto;
import com.pre_order.product_service.domain.products.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    Page<ProductResponseDto> findAllByIsShow(Pageable pageable, Boolean isShow);
    Optional<Products> findByIdAndIsShow(Long id, Boolean isShow);
    @Modifying
    @Query("update Products p set p.stock = p.stock - :quantity where p.id = :id and p.stock - :quantity >= 0")
    void updateStockById(@Param("id") Long id, @Param("quantity") Integer quantity);
}
