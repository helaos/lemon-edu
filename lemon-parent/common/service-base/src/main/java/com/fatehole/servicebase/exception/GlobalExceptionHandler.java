package com.fatehole.servicebase.exception;

import com.baomidou.mybatisplus.extension.api.R;
import com.fatehole.commonutil.ExceptionUtil;
import com.fatehole.commonutil.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/09/17:09
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error();
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("执行了自定义异常");
    }

    @ExceptionHandler(LemonException.class)
    @ResponseBody
    public Result error(LemonException e){
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return Result.error().message(e.getMsg()).code(e.getCode());
    }
}
