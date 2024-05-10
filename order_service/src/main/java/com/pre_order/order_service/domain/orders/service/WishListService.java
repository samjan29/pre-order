package com.pre_order.order_service.domain.orders.service;

import com.pre_order.order_service.domain.orders.dto.QuantityDto;
import com.pre_order.order_service.domain.orders.dto.WishListRequestDto;
import com.pre_order.order_service.domain.orders.dto.WishListResponseDto;
import com.pre_order.order_service.domain.orders.entity.WishList;
import com.pre_order.order_service.domain.orders.repository.WishListRepository;
import com.pre_order.order_service.global.exception.CustomException;
import com.pre_order.order_service.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepository wishListRepository;

    public void addToWishList(WishListRequestDto wishListRequestDto/*, Users user, Products product*/) {
//        wishListRepository.save(WishList.builder()
//                .products(product)
//                .users(user)
//                .quantity(wishListRequestDto.quantity())
//                .build());
    }

    public List<WishListResponseDto> getWishList(/*Users user*/) {
        // TODO JPQL로 변경
        // List<WishList> wishListList = wishListRepository.findAllByUsersIdAndIsDeleted(user.getId(), false);
        // return WishListResponseDto.listFrom(wishListList);
        return null;
    }

    @Transactional
    public QuantityDto updateWishList(Long wishListId, WishListRequestDto wishListRequestDto) {
        WishList wishList = wishListRepository.findById(wishListId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        wishList.updateQuantity(wishListRequestDto.quantity());
        return new QuantityDto(wishList.getQuantity());
    }
}
