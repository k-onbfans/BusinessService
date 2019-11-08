package com.accenture.business.config;

import com.accenture.business.constant.CacheNameConstants;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExpireTimeConfig {


    public Map<String, Long> getExpireTimeMap() {
        Map<String,Long> expireTimeMap = new HashMap<>();
        expireTimeMap.put(CacheNameConstants.ASYNC, (long) 30000);
        expireTimeMap.put(CacheNameConstants.TEST, (long) 120000);
        expireTimeMap.put(CacheNameConstants.DEFAULT, (long) 5*60*1000);
        return expireTimeMap;
    }
}
