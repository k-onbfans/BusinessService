package com.accenture.business.handler.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Aspect
@Component
public class LogTimeHandler {

    private long startTime;


    private static final Logger logger = LoggerFactory.getLogger(LogTimeHandler.class);

    @Pointcut(value = "@annotation(com.accenture.business.handler.aop.LogTime)")
    public void logTime() {
        //Do noting
    }

    @Before("logTime()")
    public void doBefore (){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startTime = System.currentTimeMillis();
        String str = df.format(startTime);
        logger.info("Service start time : {}",str);

    }

    @After("logTime()")
    public void doAfter (){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long endTime = System.currentTimeMillis();
        String str = df.format(endTime);
        logger.info("Service end time : {}",str);
        Long timeCost = endTime - startTime;
        logger.info("Time cost: {} ms",timeCost);
    }
}
