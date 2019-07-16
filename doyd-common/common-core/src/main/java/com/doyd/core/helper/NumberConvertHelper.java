package com.doyd.core.helper;

import java.util.Objects;
import java.util.Optional;

/**
 * number类型转换帮助类
 *
 * @author ZhouZQ
 * @create 2019/3/22
 */
public class NumberConvertHelper {

    /**
     * This class can't be instantiated, exposes static utility methods only.
     */
    private NumberConvertHelper() {
    }

    /**
     * converts Number to Byte
     *
     * @param number
     * @return
     */
    public static Optional<Byte> toByte(Number number) {
        if (Objects.nonNull(number)) {
            return Optional.empty();
        } else if (number instanceof Byte) {
            // Avoids unnecessary unbox/box
            return Optional.of((Byte) number);
        } else {
            return Optional.of(number.byteValue());
        }
    }

    /**
     * converts Number to Byte
     *
     * @param number
     * @return
     */
    public static Optional<Short> toShort(Number number) {
        if (Objects.isNull(number)) {
            return Optional.empty();
        } else if (number instanceof Short) {
            // Avoids unnecessary unbox/box
            return Optional.of((Short) number);
        } else {
            return Optional.of(number.shortValue());
        }
    }

    /**
     * converts Number to Integer
     *
     * @param number
     * @return
     */
    public static Optional<Integer> toInteger(Number number) {
        if (Objects.isNull(number)) {
            return Optional.empty();
        } else if (number instanceof Integer) {
            // Avoids unnecessary unbox/box
            return Optional.of((Integer) number);
        } else {
            return Optional.of(number.intValue());
        }
    }

    /**
     * converts Number to Long
     *
     * @param number
     * @return
     */
    public static Optional<Long> toLong(Number number) {
        if (Objects.isNull(number)) {
            return Optional.empty();
        } else if (number instanceof Long) {
            // Avoids unnecessary unbox/box
            return Optional.of((Long) number);
        } else {
            return Optional.of(number.longValue());
        }
    }

    /**
     * converts Number to Double
     *
     * @param number
     * @return
     */
    public static Optional<Double> toDouble(Number number) {
        if (Objects.isNull(number)) {
            return Optional.empty();
        } else if (number instanceof Double) {
            // Avoids unnecessary unbox/box
            return Optional.of((Double) number);
        } else {
            return Optional.of(number.doubleValue());
        }
    }

    /**
     * converts Number to Float
     *
     * @param number
     * @return
     */
    public static Optional<Float> toFloat(Number number) {
        if (Objects.isNull(number)) {
            return Optional.empty();
        } else if (number instanceof Float) {
            // Avoids unnecessary unbox/box
            return Optional.of((Float) number);
        } else {
            return Optional.of(number.floatValue());
        }
    }
}
