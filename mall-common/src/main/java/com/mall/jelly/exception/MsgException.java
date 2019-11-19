package com.mall.jelly.exception;


import com.mall.jelly.enums.ResponseCodeEnum;

/**
 * 自定义消息异常类
 *
 */
public class MsgException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 抛出异常后，返回给用户的head提示
     */
    private ResponseCodeEnum msgEnum;

    /**
     * 参数
     */
    private String[] params;

    /**
     * 需要返回信息
     */
    private Object info;

    /**
     * 调用者
     */
    private String caller;

    public void setCaller(int top) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > top + 1) {
            StackTraceElement s = stackTrace[top];
            this.caller = s.toString();
        }
    }

    public String getCaller() {
        return this.caller;
    }

    public MsgException(ResponseCodeEnum msgEnum, Object info, String... params) {
        super(msgEnum.getDesc());
        this.setCaller(3);
        this.msgEnum = msgEnum;
        this.params = params;
        this.info = info;
    }

    public MsgException(ResponseCodeEnum msgEnum, String... params) {
        super(msgEnum.getDesc());
        this.setCaller(3);
        this.msgEnum = msgEnum;
        this.params = params;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public ResponseCodeEnum getMsgEnum() {
        return msgEnum;
    }

    public void setMsgEnum(ResponseCodeEnum msgEnum) {
        this.msgEnum = msgEnum;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }


}
