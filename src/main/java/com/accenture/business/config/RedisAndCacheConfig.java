package com.accenture.business.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yifei.zhu
 */
@Configuration
public class RedisAndCacheConfig {


    @Bean
    public CacheManagerConfig cacheManagerConfig() {
        return new CacheManagerConfig();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, CacheManagerConfig cacheManagerConfig) {
        return cacheManagerConfig.buildRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, ?> redisTemplate, CacheManagerConfig cacheManagerConfig) {
        return cacheManagerConfig.buildCacheManager(redisTemplate);
    }

    @Bean
    public KeyGenerator keyGenerator(CacheManagerConfig cacheManagerConfig) {
        return cacheManagerConfig.keyGenerator();
    }

    @Bean(name = "shareKeyGenerator")
    public KeyGenerator shareKeyGenerator(CacheManagerConfig cacheManagerConfig) {
        return cacheManagerConfig.shareKeyGenerator();
    }

}