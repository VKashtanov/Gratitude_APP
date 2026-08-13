package ru.kashtanov.news_service.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.kashtanov.news_service.config.ComputingPercentageStream;

/**
 * @author Viktor Кashtanov
 */
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void savePercentageToRedis(String fileName, ComputingPercentageStream percentageStream) {
        if (percentageStream == null || percentageStream.getInputStream() == null ||
                percentageStream.getMap() == null || percentageStream.getMap().get(fileName) == null) {
            throw new NullPointerException("percentageStream is null");
        }
        Double percent = percentageStream.getMap().get(fileName);
        String value = String.valueOf(percent);
        redisTemplate.opsForValue().set(fileName, value);
    }

    public String fetchPercentageFromRedis(String fileName) {
        return redisTemplate.opsForValue().get(fileName);
    }
}
