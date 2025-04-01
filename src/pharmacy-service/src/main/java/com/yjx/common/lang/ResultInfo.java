package com.yjx.common.lang;

public enum ResultInfo {
    /**
     * 操作可能导致的结果
     */
    GLOBAL_ERROR("101", "系统繁忙"),
    SUCCESS("200", "操作成功"),
    LOGIN_SUCCESS("201", "登录成功"),
    BAD_REQUEST("402","该请求不合法，已被系统拒绝"),
    ERROR_PASSWORD("403","密码错误"),
    NOT_FOUND("404", "没有找到"),
    UNAUTHORIZED("401", "身份未知，无法访问"),
    PICTURE__FAIL("406","图片上传失败"),
    PICTURE_MAX_SIZE("407","图片不能大于1MB"),
    ERROR_VERIFICATION_CODE("408","验证码错误");


    private String code;
    private String message;

    ResultInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
