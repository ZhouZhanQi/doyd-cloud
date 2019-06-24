package com.doyd.coordinator.enums;

/**
 * @author zhouzq
 * @date 2019/6/22
 * @desc tcc状态码枚举
 */
public enum TccCodeEnum {

    /**
     * 待确认
     */
    TO_BE_CONFIRMED,

    /**
     * 确认
     */
    CONFIRMED,

    /**
     * 冲突
     */
    CONFLICT,

    /**
     * 超时
     */
    TIMEOUT
}
