package com.yjx.common.exception;

import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handle(ShiroException e) {
        log.error("运行时异常：-------------{}", e);
        return Result.error().codeAndMessage(ResultInfo.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handle(MethodArgumentNotValidException e) {
        log.error("实体校验异常：-------------{}", e);

        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();

        return Result.error().codeAndMessage(ResultInfo.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(value = MyRuntimeException.class)
    public Result handle(MyRuntimeException e) {
        log.error("运行时异常：-------------{}", e);
        return Result.error().codeAndMessage(e.getCode(),e.getMessage());
    }
}