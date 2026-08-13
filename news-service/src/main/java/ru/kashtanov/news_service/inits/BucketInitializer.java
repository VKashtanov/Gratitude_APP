package ru.kashtanov.news_service.inits;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Viktor Кashtanov
 */
@Slf4j
@Getter
@Component
public class BucketInitializer {
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public BucketInitializer(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @PostConstruct
    public void init() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info("Make bucket successfully");
        }
    }
}
