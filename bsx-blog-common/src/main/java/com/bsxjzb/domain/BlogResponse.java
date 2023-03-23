package com.bsxjzb.domain;

import com.bsxjzb.constants.enums.AppHttpCodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class BlogResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String msg;
    private Integer code;
    private T data;

    private BlogResponse() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
    }

    private BlogResponse(String msg, Integer code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    private BlogResponse(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    private BlogResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static BlogResponse ok() {
        return new BlogResponse();
    }

    public static BlogResponse ok(Object data) {
        return new BlogResponse(AppHttpCodeEnum.SUCCESS.getMsg(), AppHttpCodeEnum.SUCCESS.getCode(), data);
    }

    public static BlogResponse ok(Object data, String msg) {
        return new BlogResponse(msg, AppHttpCodeEnum.SUCCESS.getCode(), data);
    }

    public static BlogResponse error(String msg, Integer code) {
        return new BlogResponse(msg, code);
    }
    public static BlogResponse error(AppHttpCodeEnum appHttpCodeEnum) {
        return new BlogResponse(appHttpCodeEnum.getMsg(), appHttpCodeEnum.getCode());
    }
    public static BlogResponse error(AppHttpCodeEnum appHttpCodeEnum, String msg) {
        return new BlogResponse(msg, appHttpCodeEnum.getCode());
    }
}
