package com.bsxjzb.constants.enums;

public enum AppHttpCodeEnum {
    SUCCESS(200,"操作成功");

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
