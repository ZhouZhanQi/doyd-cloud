package com.doyd.core.exceptions;

/**
 *
 * @author zhouzq
 * @date 2019/7/16
 * @desc 业务逻辑异常
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -767923334338389332L;
    /**
     * 错误代码
     */
    protected int errCode = 500;

    public BusinessException(int code) {
        super();
        this.errCode = code;
    }

    public BusinessException() {
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public BusinessException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

}
