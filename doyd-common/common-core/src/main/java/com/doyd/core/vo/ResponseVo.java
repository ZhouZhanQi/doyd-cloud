package com.doyd.core.vo;

import com.doyd.core.vo.enums.CommonCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhouzq
 * @date 2019/5/27
 * @desc 接口返回值统一封装类
 */
@ApiModel(value = "接口返回值统一封装类")
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {

    @ApiModelProperty("接口调用是否成功")
    private boolean success;//处理成功或者失败

    @ApiModelProperty("接口返回编码")
    private int code;

    @ApiModelProperty("接口返回消息")
    private String message;

    @ApiModelProperty("接口返回数据")
    private T data;

    public ResponseVo(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static final ResponseVo success(){
        return new ResponseVo(true, CommonCodeEnum.SUCCESS.getCode(), null, null);
    }

    public static final ResponseVo failure(){
        return new ResponseVo(false, CommonCodeEnum.ERROR.getCode(), null, null);
    }

    public static final ResponseVo success(String message){
        return new ResponseVo(true, CommonCodeEnum.SUCCESS.getCode(), message, null);
    }

    public static final ResponseVo failure(String message){
        return new ResponseVo(false, CommonCodeEnum.ERROR.getCode(), message, null);
    }

    public static final ResponseVo success(int code, String message){
        return new ResponseVo(true, code, message, null);
    }

    public static final ResponseVo failure(int code, String message){
        return new ResponseVo(false, code, message, null);
    }

    public static  final <T> ResponseVo<T> success(T obj){
        return new ResponseVo<>(true, CommonCodeEnum.SUCCESS.getCode(), null, obj);
    }

    public static  final <T> ResponseVo<T> failure(T obj){
        return new ResponseVo<>(false, CommonCodeEnum.ERROR.getCode(), null, obj);
    }
    public static  final <T> ResponseVo<T> failure(CommonCodeEnum commonCodeEnum){
        return new ResponseVo<>(false, commonCodeEnum.getCode(), commonCodeEnum.getDesc(), null);
    }

    public static final <T> ResponseVo<T> success(String message, T obj){
        return new ResponseVo<>(true, CommonCodeEnum.SUCCESS.getCode(), message, obj);
    }

    public static final <T> ResponseVo<T> failure(String message, T obj){
        return new ResponseVo<>(false, CommonCodeEnum.ERROR.getCode(), message, obj);
    }


    public static final <T> ResponseVo<T> success(int code, String message, T obj) {
        return new ResponseVo<>(true, code, message, obj);
    }

}
