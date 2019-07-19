package com.doyd.core.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 随机对象帮助类
 *
 * @author zhouzq
 * @date 2018-05-28
 */
@Slf4j
public final class SecureRandomUtils {

    private SecureRandomUtils() {

    }


    /**
     * Get strong enough SecureRandom instance and of the checked exception.
     * TODO Try {@code NativePRNGNonBlocking} and failover to default SHA1PRNG until Java 9.
     *
     * @return the strong instance
     */
    public static SecureRandom getNativeInstance() {
        try {
            return SecureRandom.getInstance("NativePRNGNonBlocking");
        } catch (final NoSuchAlgorithmException e) {
            log.warn(e.toString());
            return new SecureRandom();
        }
    }
}
