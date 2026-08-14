package ru.kashtanov.news_service.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.kashtanov.news_service.config.ComputingPercentageStream;
import ru.kashtanov.news_service.exceptions.NewsCrudException;
import ru.kashtanov.news_service.exceptions.RedisCrudException;

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
            throw new NewsCrudException("ComputingPercentageStream is null");
        }
        Double percent = percentageStream.getMap().get(fileName);
        String value = String.valueOf(percent);
        redisTemplate.opsForValue().set(fileName, value);
    }

    public String fetchPercentageFromRedis(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new NewsCrudException("FileName is null or blank");
        }
        String numberStr = redisTemplate.opsForValue().get(fileName);
        if (numberStr == null || numberStr.isBlank()) {
            throw new RedisCrudException("No percentage found for key");
        }
        return numberStr;
    }

    public void deletePercentageFromRedis(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("fileName is null or blank");
        }
        Boolean isDeleted = redisTemplate.delete(fileName);
        if(!isDeleted) {
            throw new RedisCrudException("There is no file to delete");
        }
    }
}
