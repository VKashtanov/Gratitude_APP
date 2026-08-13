package ru.kashtanov.news_service.config;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

/**
 * @author Viktor Кashtanov
 */
@Slf4j
@Configuration
public class MinioConfig  {

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String endpoint;


    @Bean
    public MinioClient minioClient() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.debug("CREATING: " + Instant.now().toString());
        MinioClient build = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

        build.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        return build;
    }




}
