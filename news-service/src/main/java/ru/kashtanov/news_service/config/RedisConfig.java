package ru.kashtanov.news_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

/**
 * @author Viktor Кashtanov
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory,
                                                       ObjectMapper objectMapper) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // keys
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // value
        var redisSerializer = new GenericJacksonJsonRedisSerializer(objectMapper);
        template.setValueSerializer(redisSerializer);
        template.setHashValueSerializer(redisSerializer);

        template.afterPropertiesSet();// to check if all properties are set, then start complete bean creation
        return template;
    }
}
