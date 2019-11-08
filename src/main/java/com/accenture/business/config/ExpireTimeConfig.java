package com.accenture.business.config;

import com.accenture.business.constant.CacheNameConstants;

import java.util.Map;

public class ExpireTimeConfig {

    private Map<String,Long> ExpireTimeMap;

    public Map<String, Long> getExpireTimeMap() {
        ExpireTimeMap.put(CacheNameConstants.ASYNC, (long) 30000);
        ExpireTimeMap.put(CacheNameConstants.TEST, (long) 120000);
        ExpireTimeMap.put(CacheNameConstants.DEFAULT, (long) 5*60*1000);
        return ExpireTimeMap;
    }

    public void setExpireTimeMap(Map<String, Long> expireTimeMap) {
        ExpireTimeMap = expireTimeMap;
    }
}
