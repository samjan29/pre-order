package com.pre_order.order_service.global.security.encrypt;

import com.pre_order.order_service.global.exception.CustomException;
import com.pre_order.order_service.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
public class AES256Util {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private static String key;
    private static String iv;

    @Value("${aes256.secret.key}")
    public void setKey(String key) {
        AES256Util.key = key;
    }

    @Value("${aes256.secret.iv}")
    public void setIv(String iv) {
        AES256Util.iv = iv;
    }

    public String encrypt(String str) {
        try {
            byte[] byteKey = key.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(str.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            log.error("AES256Util encrypt error", e);
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }

    public String decrypt(String str) {
        try {
            byte[] byteKey = key.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(str));
            return new String(decrypted);

        } catch (Exception e) {
            log.error("AES256Util decrypt error", e);
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }
}
