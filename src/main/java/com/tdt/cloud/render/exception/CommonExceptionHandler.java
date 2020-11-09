package com.tdt.cloud.render.exception;

import com.tdt.cloud.render.commons.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author Mr.superbeyone
 * @project online-data-manager
 * @className CommonExceptionHandler
 * @description 统一异常处理Handler
 * @date 2019-03-20 13:44
 **/
@RestControllerAdvice
public class CommonExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    public JsonResult handle(Exception e) {
        if (e instanceof CommonException) {
            CommonException ce = (CommonException) e;
            logger.error(((CommonException) e).getMsg());
            return JsonResult.fail(ce.getCode(), ce.getMsg());
        } else if (e instanceof NoHandlerFoundException) {
            e.printStackTrace();
            return JsonResult.fail(404, "404 page");
        } else {
            logger.error("[系统异常]", e.getMessage());
            e.printStackTrace();
            String msg = "系统繁忙，请稍后重试";
            return JsonResult.fail(9999, msg);
        }
    }
}
