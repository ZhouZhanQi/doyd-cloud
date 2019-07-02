package com.doyd.core.vo.enums;

/**
 * @author zhouzq
 * @date 2019/5/27
 * @desc 返回值公共错误码
 */
public enum CommonCodeEnum {

    SUCCESS(200,"SUCCESS"),

    ERROR(300,"ERROR");

    private final int code;

    private final String desc;


    CommonCodeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }


    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
