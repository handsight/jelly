package com.mall.jelly.enums;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public enum ResponseCodeEnum {

    /*************通用的错误码 5001XX**************/
    INVALID("-1", "無效狀態", "Invalid State", "無効な状態", "無效狀態"),
    SUCCESS("0000", "操作成功", "Successful Operation", "成功した操作", "操作成功"),
    UNKNOWN_ERROR("100000", "未知错误", "未知错误", "未知错误", "未知错误"),
    FAILURE("100001", "操作失败", "Operation Failed", "操作が失敗しました", "操作失敗"),
    APP_MAINTAIN("100002", "%s系统维护中，预计完成时间%s，给您带来不便深感抱歉，感谢您的理解与支持。", "系统维护中，预计完成时间%s，给您带来不便深感抱歉，感谢您的理解与支持。", "系统维护中，预计完成时间%s，给您带来不便深感抱歉，感谢您的理解与支持。", "系统维护中，预计完成时间%s，给您带来不便深感抱歉，感谢您的理解与支持。"),
    FAILURE_FOR_ADMIN("100003", "操作失败，请联系客服", "操作失败，请联系客服", "操作失败，请联系客服", "操作失败，请联系客服"),
    SERVER_ERROR("500100", "服务端异常", "服务端异常", "服务端异常", "服务端异常"),
    PARAMETER_ERROR("500101", "请求参数错误", "请求参数错误", "请求参数错误", "请求参数错误"),
    REQUEST_ILLEGAL("500102", "请求非法", "请求非法", "请求非法", "请求非法"),
    SERVICE_NAME_TO_URL_NOT_FOUND("500104", "系統繁忙，請稍後重試。", "The system is busy, please try again later", "システムがビジー状態です。後でやり直してください。", "系統繁忙，請稍後重試。"),
    PARAMETER_HEADER_ERROR("500105", "请求头参数错误", "请求头参数错误：%s", "请求头参数错误", "请求头参数错误"),
    AUTHORITY_ILLEGAL("500106", "您没有权限执行该项操作", "您没有权限执行该项操作", "您没有权限执行该项操作", "您没有权限执行该项操作"),
    FUNC_DELAY("500107", "功能暂未开放", "功能暂未开放", "功能暂未开放", "功能暂未开放"),
    WALLET_DIRECTION("500108", "不支持钱包(%s)%s的操作(%s)", "不支持钱包(%s)%s的操作(%s)", "不支持钱包(%s)%s的操作(%s)", "不支持钱包(%s)%s的操作(%s)"),
    SERVER_BUSY("500109", "服务端繁忙", "服务端繁忙", "服务端繁忙", "服务端繁忙"),
    STR("500110", "%s", "%s", "%s", "%s"),
    API_FORBIDDEN("500111", "API接口%s被禁用", "API接口%s被禁用", "API接口%s被禁用", "API接口%s被禁用"),


    /*************登录模块 5002XX**************/
    PASSWORD_ERROR("500201", "密码错误", "密码错误", "密码错误", "密码错误"),
    SIGNATURE_TIMEOUT("500202", "請重新登錄账户。", "Please login again.", "トークンの有効期限が切れました。再度ログインしてください", "請重新登錄账户。"),
    SIGNATURE_ERROR("500203", "請重新登錄账户。", "Please login again.", "悪いトークン、ログインしてください", "請重新登錄账户。"),
    ADD_USER_ERROR("500204", "用戶名已經被占用，請嘗試其他名字。", "Username is already occupied, please try other username.", "ユーザー名は既に占有されています", "用戶名已經被占用，請嘗試其他名字。"),
    LOGIN_ERROR("500205", "用户名或密码错误", "Account or password is incorrect, please check again.", "ログインに失敗しました。アカウントまたはパスワードが正しくありません。", "賬號或者密碼錯誤，請重新輸入。"),
    USER_TYPE_ERROR("500206", "此账号不能用游戏中已注册账号，请重新注册", "此账号不能用游戏中已注册账号，请重新注册", "此账号不能用游戏中已注册账号，请重新注册", "此账号不能用游戏中已注册账号，请重新注册"),
    LOGIN_LOCK("500207", "登陆锁定中，请联系管理员", "登陆锁定中，请联系管理员", "登陆锁定中，请联系管理员", "登陆锁定中，请联系管理员"),
    LOGIN_LOCK_NOT_OUT("500208", "登陆锁定中，请联系管理员", "登陆锁定中，请联系管理员", "登陆锁定中，请联系管理员", "登陆锁定中，请联系管理员"),



    /************资料修改***************/
    UPDATE_PHONE_EXISTENCE("9000", "账户已註冊賬號，不允許用於修改。", "The phone number is already registered，cannot be used to modifie.", "新しい携帯電話番号が登録されています", "該手機號碼已註冊賬號，不允許用於修改。"),

    VERSION_ERROR("9001", "版本号错误", "Wrong version", "間違ったバージョン番号", "版本號錯誤");

    /**
     * 状态值
     */
    private String status;

    /**
     * 状态描述
     */
    private String desc;//中文-简体

    private String descEN;//英文
    private String descJA;//日文
    private String descTW;//中文-繁体

    ResponseCodeEnum(String status, String desc) {
        this.status = checkStatus(status);
        this.desc = desc;
        this.descEN = desc;
        this.descJA = desc;
        this.descTW = desc;
    }

    ResponseCodeEnum(String status, String desc, String descEN, String descJA, String descTW) {
        this.status = checkStatus(status);
        this.desc = desc;
        this.descEN = descEN;
        this.descJA = descJA;
        this.descTW = descTW;
    }

    public String getDictionaryName() {
        return desc;
    }

    public String getDictionaryCode() {
        return "response_code";
    }

    public String getDictionaryValue() {
        return status;
    }

    public String getDictionaryDescribe() {
        return desc;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public String getDescEN() {
        return descEN;
    }

    public String getDescJA() {
        return descJA;
    }

    public String getDescTW() {
        return descTW;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 根据状态值获取枚举值
     *
     * @param status
     * @return
     */
    public static ResponseCodeEnum getStatus(String status) {
        for (ResponseCodeEnum e : ResponseCodeEnum.values()) {
            if (e.getStatus() == status) {
                return e;
            }
        }

        return INVALID;
    }

    /**
     * 根据状态值获取枚举值
     *
     * @param status
     * @return
     */
    public static ResponseCodeEnum getStatus(Integer status) {
        return status == null ? INVALID : getStatus(status.intValue());
    }

    private static HashMap<String, Integer> status2Exist;

    private static String checkStatus(String key) {
        if (status2Exist == null) {
            status2Exist = new HashMap<>();
        }
        Integer exist = status2Exist.get(key);
        if (exist != null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            Integer top = 3;
            String caller = "";
            if (stackTrace.length > top + 1) {
                StackTraceElement s = stackTrace[top];
                caller = s.toString();
            }
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            System.err.printf("%s   ERR : ResponseCodeEnum 出现重复 status（%s） : %s\n", sdf.format(now), key, caller);
            try {
                Thread.sleep(5000);//停留5秒 使开发人员 引起注意
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        status2Exist.put(key, 1);
        return key;
    }
}