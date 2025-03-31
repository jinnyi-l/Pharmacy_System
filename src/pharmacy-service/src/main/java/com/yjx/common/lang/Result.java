package com.yjx.common.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CodeVerse
 * admin10/11 16:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result implements Serializable{

    /**
     * 返回成功或者失败
     */
    private boolean status;

    /**
     * 返回状态码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Map<String, Object> data = new HashMap<>();


    public static Result success() {
        Result result = new Result();
        result.status = true;
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.status = false;
        return result;
    }

    public Result code(String code) {
        this.setCode(code);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result codeAndMessage(String code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public Result codeAndMessage(ResultInfo resultInfo) {
        this.setCode(resultInfo.getCode()).setMessage(resultInfo.getMessage());
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
