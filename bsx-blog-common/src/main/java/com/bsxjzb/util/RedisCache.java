package com.bsxjzb.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    public void incrementCacheMapValue(final String key, final String hKey, final int v) {
        redisTemplate.opsForHash().increment(key, hKey, v);
    }

    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, hKey);
    }

}
