package com.pre_order.core.domain.users.service;

import com.pre_order.core.domain.users.dto.UsersInfoRequestDto;
import com.pre_order.core.domain.users.entity.Users;
import com.pre_order.core.domain.users.repository.UsersCustomRepository;
import com.pre_order.core.domain.users.repository.UsersRepository;
import com.pre_order.core.global.exception.CustomException;
import com.pre_order.core.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersCustomRepository usersCustomRepository;

    private final PasswordEncoder passwordEncoder;

    public void signUp(UsersInfoRequestDto usersInfoRequestDto) {
        final String email = usersInfoRequestDto.email();
        final String phoneNumber = usersInfoRequestDto.phoneNumber();

        checkEmailDuplicate(email);
        checkPhoneNumberDuplicate(phoneNumber);

        // TODO 카카오 API로 요청해서 zipCode 받아오기

        usersRepository.save(Users.builder()
                .email(email)
                .password(passwordEncoder.encode(usersInfoRequestDto.password()))
                .name(usersInfoRequestDto.name())
                .phoneNumber(phoneNumber)
                .address1(usersInfoRequestDto.address1())
                .address2(usersInfoRequestDto.address2())
                .build());
    }

    private void checkEmailDuplicate(String email) {
        final Long countEmail = usersCustomRepository.countUserByEmail(email);
        if (countEmail > 0) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    private void checkPhoneNumberDuplicate(String phoneNumber) {
        final Long countPhoneNumber = usersCustomRepository.countUserByPhoneNumber(phoneNumber);
        if (countPhoneNumber > 0) {
            throw new CustomException(ErrorCode.DUPLICATED_PHONE_NUMBER);
        }
    }
}
