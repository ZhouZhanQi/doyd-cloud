package com.doyd.core.util.encrypt;

import com.doyd.core.util.SecurityUtils;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Optional;

/**
 * aes加密
 *
 * @author ZhouZQ
 * @create 2019/3/21
 */
@Slf4j
public class AesUtil {

    private static final String ALGORITHM_STRING = "AES/ECB/PKCS5Padding";

    /**
     * 字符编码
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 密钥长度
     */
    private static final int KEY_SIZE = 16;

    private static SecretKeySpec getSecretKeySpec(String password) throws Exception {
        byte[] rByte;

        if (!StringUtils.isBlank(password)) {
            rByte = password.getBytes(CHARSET_NAME);
        } else {
            rByte = new byte[24];
        }
        return new SecretKeySpec(rByte, "AES");
    }

    /**
     * @param content
     * @param password 必须是16位长度
     * @return
     */
    private static Optional<byte[]> encrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_STRING);
            byte[] byteContent = content.getBytes(CHARSET_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(password));
            return Optional.of(cipher.doFinal(byteContent));
        } catch (Exception e) {
            log.error("encrypt error", e);
        }
        return Optional.empty();
    }

    /**
     * @param content
     * @param password 必须是16位长度
     * @return
     */
    private static Optional<byte[]> decrypt(byte[] content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_STRING);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(password));
            return Optional.of(cipher.doFinal(content));
        } catch (Exception e) {
            log.error("decrypt error", e);
        }
        return Optional.empty();
    }

    /**
     * 加密
     */
    public static Optional<String> aesEncode(String content, String keyBytes) {

        Preconditions.checkNotNull(content, "aes encode content is null");
        Preconditions.checkNotNull(keyBytes, "aes encode key is null");

        Optional<String> optionalMd5 = SecurityUtils.md5Encrypt(keyBytes);
        if (!optionalMd5.isPresent()) return Optional.empty();

        String key = optionalMd5.get().substring(8, 24);
        Optional<byte[]> optionalBytes = encrypt(content, key);
        return optionalBytes.map(bytes -> Base64.getEncoder().encodeToString(bytes));
    }

    /**
     * 解密
     */
    public static Optional<String> aesDecode(String content, String keyBytes) {
        Preconditions.checkNotNull(content, "aes decode content is null");
        Preconditions.checkNotNull(keyBytes, "aes decode key is null");

        Optional<String> optionalMd5 = SecurityUtils.md5Encrypt(keyBytes);
        if (!optionalMd5.isPresent()) return Optional.empty();

        String key = optionalMd5.get().substring(8, 24);
        Optional<byte[]> optionalBytes = decrypt(Base64.getDecoder().decode(content), key);

        try {
            return Optional.of(new String(optionalBytes.orElseThrow(IllegalStateException::new), CHARSET_NAME));
        } catch (UnsupportedEncodingException | IllegalStateException e) {
            log.error("aesDecode error", e);
            return Optional.empty();
        }
    }

    /**
     * 加密
     */
    public static Optional<String> aesEncode2(String content, String keyBytes) {
        Preconditions.checkNotNull(content,"aes encode2 content is null");
        Preconditions.checkNotNull(keyBytes, "aes encode2 key is null");

        Preconditions.checkState(keyBytes.length() == KEY_SIZE, "the secret key must be 16 bit");
        Optional<byte[]> optionalBytes = encrypt(content, keyBytes);

        return optionalBytes.map(bytes -> Base64.getEncoder().encodeToString(bytes));
    }

    /**
     * 解密
     */
    public static Optional<String> aesDecode2(String content, String keyBytes) {
        Preconditions.checkNotNull(content,"aes decode2 content is null");
        Preconditions.checkNotNull(keyBytes, "aes decode2 key is null");
        Preconditions.checkState(keyBytes.length() == KEY_SIZE, "the secret key must be 16 bit");
        Optional<byte[]> optionalBytes = decrypt(Base64.getDecoder().decode(content), keyBytes);

        try {
            return Optional.of(new String(optionalBytes.orElseThrow(IllegalStateException::new), CHARSET_NAME));
        } catch (UnsupportedEncodingException | IllegalStateException e) {
            log.error("aesDecode2 error", e);
            return Optional.empty();
        }
    }
}
