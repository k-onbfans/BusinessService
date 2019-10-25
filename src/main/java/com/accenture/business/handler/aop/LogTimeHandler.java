package com.accenture.business.handler.aop;

import com.accenture.business.handler.log.RequestBodyLogger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class LogTimeHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestBodyLogger.class);

    @Pointcut(value = "@annotation(com.accenture.business.handler.aop.LogTime)")
    public void LogTime() {
    }

    @Before("LogTime()")
    public void doBefore (){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("Service start time :" + df.format(new Date()));
    }

    @After("LogTime()")
    public void doAfter (){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("Service end time :" + df.format(new Date()));
    }
}
