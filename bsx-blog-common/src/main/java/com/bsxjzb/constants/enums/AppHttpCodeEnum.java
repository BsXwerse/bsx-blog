package com.bsxjzb.constants.enums;

public enum AppHttpCodeEnum {
    SUCCESS(200,"操作成功"),
    SYSTEM_ERROR(500, "系统错误"),
    NEED_LOGIN(401, "请登录后操作"),
    REQUIRE_USERNAME(501, "必须填写用户名");

    private int code;
    private String msg;
    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
