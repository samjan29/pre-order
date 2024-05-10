package com.pre_order.order_service.domain.orders.service;

import com.pre_order.order_service.domain.orders.client.ProductsClient;
import com.pre_order.order_service.domain.orders.dto.ProductInfoResponseDto;
import com.pre_order.order_service.domain.orders.dto.QuantityDto;
import com.pre_order.order_service.domain.orders.entity.Orders;
import com.pre_order.order_service.domain.orders.repository.OrdersRepository;
import com.pre_order.order_service.domain.orders.status.OrderStatus;
import com.pre_order.order_service.global.exception.CustomException;
import com.pre_order.order_service.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private final ProductsClient productsClient;

    public void createOrder(Long productId, QuantityDto quantityDto, Long userId) {
        Integer quantity = quantityDto.quantity();
        Integer productPrice = checkProduct(productId, quantity);

        ordersRepository.save(Orders.builder()
                .userId(userId)
                .totalPrice(productPrice * quantity)
                .status(OrderStatus.ORDERED)
                .build());

        updateStock(productId, quantity);
    }

    private Integer checkProduct(Long productId, Integer quantity) {
        ResponseEntity<ProductInfoResponseDto> responseEntity = productsClient.checkProduct(productId);

        if (responseEntity.getStatusCode().isError()) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        ProductInfoResponseDto result = responseEntity.getBody();

        if (result == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        if (result.stock() - quantity < 0) {
            throw new CustomException(ErrorCode.INVALID_QUANTITY);
        }

        return result.price();
    }

    private void updateStock(Long productId, Integer quantity) {
        ResponseEntity<Void> responseEntity = productsClient.updateStock(productId, quantity);

        if (responseEntity.getStatusCode().isError()) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }
}
