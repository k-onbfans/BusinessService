package com.accenture.business.handler;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    private Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException error){
        return setErrorMap(error);
    }

    @ExceptionHandler(value = InterruptedException.class)
    @ResponseBody
    private Map<String, Object> handleInterruptedException(InterruptedException error){
        return setErrorMap(error);
    }

    @ExceptionHandler(value = ExecutionException.class)
    @ResponseBody
    private Map<String, Object> handleExecutionException(ExecutionException error){
        return setErrorMap(error);
    }

    private Map<String, Object> setErrorMap(Exception e){
        Map<String, Object> map = new HashMap<>();
        map.put("defaultMessage",e.getMessage());
        map.put("error","Bad Request");
        map.put("status",400);
        return map;
    }
}
