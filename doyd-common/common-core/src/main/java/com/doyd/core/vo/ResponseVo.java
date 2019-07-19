package com.doyd.core.vo;

import com.doyd.core.util.JacksonUtils;
import com.doyd.core.util.encrypt.AesUtil;
import com.doyd.core.vo.enums.CommonCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

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
@JsonPropertyOrder({"success", "data", "securityData", "errCode", "errMsg", "errDetail"})
public class ResponseVo<T> {

    @ApiModelProperty(value = "成功标记", example = "true")
    private boolean success;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String securityKey;

    @ApiModelProperty("加密保存的字段")
    private String securityData;

    @ApiModelProperty("错误码")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int errCode;

    @ApiModelProperty("错误信息")
    private String errMsg;

    @ApiModelProperty("错误详细")
    private Object errDetail;

    @ApiModelProperty("响应的数据")
    private T data;

    /**
     * 创建一个没有返回数据的成功的返回信息对象。
     *
     * @return
     */
    public static ResponseVo success() {
        return success(null);
    }

    /**
     * 创建一个成功的返回信息对象, 并设置返回数据。
     *
     * @param data 返回的数据
     * @param <E>  返回数据的类型
     * @return
     */
    public static <E> ResponseVo<E> success(E data) {
        return success(null, data);
    }

    /**
     * 创建一个成功的返回信息对象,并根据securityKey决定是否加密返回
     *
     * @param securityKey 加密密钥,当密钥不为空时,将进行加密,返回中的data将替换为securityData
     * @param data        返回数据
     * @param <E>         返回数据类型
     * @return
     */
    public static <E> ResponseVo<E> success(String securityKey, E data) {
        ResponseVo<E> vo = new ResponseVo<>();
        vo.success = true;
        vo.setSecurityKey(securityKey);
        vo.setData(data);
        return vo;
    }

    /**
     * 创建一个失败的返回信息对象, 并设置错误信息。
     *
     * @param msg 错误信息
     * @return
     */
    public static <E> ResponseVo<E> fail(String msg) {
        return fail(0, msg);
    }

    /**
     * 创建一个失败的返回信息对象, 并设置错误代码和错误信息。
     *
     * @param errCode 错误代码
     * @param errMsg  错误信息
     * @return
     */
    public static <E> ResponseVo<E> fail(int errCode, String errMsg) {
        return fail(errCode, errMsg, null);
    }

    /**
     * 创建一个失败的返回信息对象, 并设置错误代码，错误信息和错误详细。
     *
     * @param errCode   错误代码
     * @param errMsg    错误消息
     * @param errDetail 错误详细
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <E> ResponseVo<E> fail(int errCode, String errMsg, Object errDetail) {
        ResponseVo vo = new ResponseVo();
        vo.success = false;
        vo.errCode = errCode;
        vo.errMsg = errMsg;
        vo.errDetail = errDetail;
        return vo;
    }

    public void setData(T data) {
        if (StringUtils.isBlank(securityKey)) {
            //不加密
            this.data = data;
        } else {
            //加密
            if (data instanceof String) {
                securityData = AesUtil.aesEncode(String.valueOf(data), securityKey).orElse("");
            } else {
                securityData = AesUtil.aesEncode(JacksonUtils.pojoTojson(data), securityKey).orElse("");
            }
        }
    }
}
