package com.bsxjzb.constants.enums;

public enum AppHttpCodeEnum {
    SUCCESS(200,"操作成功"),
    SYSTEM_ERROR(500, "系统错误"),
    NEED_LOGIN(401, "请登录后操作"),
    NO_OPERATOR_AUTH(402, "无权操作"),
    LOGIN_ERROR(502, "用户名或密码错误"),
    REQUIRE_USERNAME(501, "必须填写用户名"),
    FILE_TYPE_ERROR(502, "不支持的文件类型"),
    EMPTY_FILE_ERROR(504, "文件不能为空"),
    CONTENT_NOT_NULL(503, "评论不能为空"),
    USERNAME_NOT_NULL(505, "用户名不能为空"),
    PASSWORD_NOT_NULL(506, "密码不能为空"),
    EMAIL_NOT_NULL(507, "邮箱不能为空"),
    NICKNAME_NOT_NULL(508, "昵称不能为空"),
    USERNAME_EXIST(509, "该用户名已经存在"),
    NICKNAME_EXIST(510, "该昵称已经存在");

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
