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
import java.sql.Date;

@Aspect
@Component
public class LogTimeHandler {

    private static long startTime;

    private static long endTime;


    private static final Logger logger = LoggerFactory.getLogger(RequestBodyLogger.class);

    @Pointcut(value = "@annotation(com.accenture.business.handler.aop.LogTime)")
    public void LogTime() {
    }

    @Before("LogTime()")
    public void doBefore (){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startTime = System.currentTimeMillis();
        logger.info("Service start time :" + df.format(startTime));
    }

    @After("LogTime()")
    public void doAfter (){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        endTime = System.currentTimeMillis();
        logger.info("Service end time :" + df.format(endTime));
        logger.info("Time cost:" + (endTime - startTime) +"ms");
    }
}
