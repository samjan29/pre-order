package com.pre_order.core.domain.orders.service;

import com.pre_order.core.domain.orders.dto.QuantityDto;
import com.pre_order.core.domain.orders.dto.WishListRequestDto;
import com.pre_order.core.domain.orders.dto.WishListResponseDto;
import com.pre_order.core.domain.orders.entity.WishList;
import com.pre_order.core.domain.orders.repository.OrdersRepository;
import com.pre_order.core.domain.orders.repository.WishListRepository;
import com.pre_order.core.domain.products.entity.Products;
import com.pre_order.core.domain.users.entity.Users;
import com.pre_order.core.global.exception.CustomException;
import com.pre_order.core.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final WishListRepository wishListRepository;

    public void addToWishList(WishListRequestDto wishListRequestDto, Users user, Products product) {
        wishListRepository.save(WishList.builder()
                .products(product)
                .users(user)
                .quantity(wishListRequestDto.quantity())
                .build());
    }

    public List<WishListResponseDto> getWishList(Users user) {
        // TODO JPQL로 변경
        List<WishList> wishListList = wishListRepository.findAllByUsersIdAndIsDeleted(user.getId(), false);
        return WishListResponseDto.listFrom(wishListList);
    }

    @Transactional
    public QuantityDto updateWishList(Long wishListId, WishListRequestDto wishListRequestDto) {
        WishList wishList = wishListRepository.findById(wishListId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        wishList.updateQuantity(wishListRequestDto.quantity());
        return new QuantityDto(wishList.getQuantity());
    }
}
