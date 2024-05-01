package com.pre_order.user_service.global.security.encrypt;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter
public class EncryptConverter implements AttributeConverter<String, String> {

    private final AES256Util aes256Util = new AES256Util();

    @Override
    public String convertToDatabaseColumn(String s) {
        return Optional.ofNullable(s)
                .map(aes256Util::encrypt)
                .orElse(null);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return Optional.ofNullable(s)
                .map(aes256Util::decrypt)
                .orElse(null);
    }
}
