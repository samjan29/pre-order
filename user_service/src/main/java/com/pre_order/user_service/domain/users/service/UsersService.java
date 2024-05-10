package com.pre_order.user_service.domain.users.service;

import com.pre_order.user_service.domain.users.dto.UserInfoDto;
import com.pre_order.user_service.domain.users.dto.UsersInfoRequestDto;
import com.pre_order.user_service.domain.users.entity.Users;
import com.pre_order.user_service.domain.users.repository.UsersCustomRepository;
import com.pre_order.user_service.domain.users.repository.UsersRepository;
import com.pre_order.user_service.global.exception.CustomException;
import com.pre_order.user_service.global.exception.ErrorCode;
import com.pre_order.user_service.global.security.encrypt.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersCustomRepository usersCustomRepository;

    private final PasswordEncoder passwordEncoder;
    private final AES256Util aes256Util;

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

    @Transactional
    public UserInfoDto updateUserInfo(UserInfoDto userInfoDto, Users user) {
        // null인 데이터는 update를 하지 않고 처리하면 성능 향상이 되는가?
        usersCustomRepository.updateAddressAndPhoneNumber(
                user.getId(),
                userInfoDto.getAddress1(),
                userInfoDto.getAddress2(),
                userInfoDto.getPhoneNumber());

        return userInfoDto;
    }

    @Transactional
    public void updatePassword(String password, Users user) {
        // TODO 같은 비밀번호인 경우 예외 처리
        usersCustomRepository.updatePassword(user.getId(), passwordEncoder.encode(password));
    }

    public String getUserId(Long id) {
        return aes256Util.encrypt(String.valueOf(id));
    }
}
