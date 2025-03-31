package com.yjx.common.exception;

import com.yjx.common.lang.ResultInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author CodeVerse
 * admin10/11 16:56
 **/
public class MyRuntimeException extends RuntimeException {

    private String code;
    private String message;

    public MyRuntimeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public MyRuntimeException(ResultInfo resultInfo){
        this.code = resultInfo.getCode();
        this.message = resultInfo.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
