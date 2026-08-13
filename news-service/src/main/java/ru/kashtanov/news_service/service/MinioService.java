package ru.kashtanov.news_service.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kashtanov.news_service.config.ComputingPercentageStream;
import ru.kashtanov.news_service.exceptions.MinioS3CustomException;
import ru.kashtanov.news_service.inits.BucketInitializer;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author Viktor Кashtanov
 */
@Slf4j
@Service
public class MinioService {
    private final MinioClient minioClient;
    private final BucketInitializer bucketInitializr;
    private final RedisService redisService;

    public MinioService(MinioClient minioClient, BucketInitializer bucketInitializr, RedisService redisService) {
        this.minioClient = minioClient;
        this.bucketInitializr = bucketInitializr;
        this.redisService = redisService;
    }

    // there are several main methods of working with MinioClient
    public String addFile(MultipartFile file) {
        // validation
        if (file == null || file.getSize() <= 0) {
            throw new MinioS3CustomException("File can't be processed since it is empty");
        }
        String fileName = formName(file);
        String bucketName = bucketInitializr.getBucketName();
        try {
            uploadFile(file, fileName, bucketName);
        } catch (IOException e) {
            throw new MinioS3CustomException("Impossible to add file");
        }
        return fileName;

    }

    private void uploadFile(MultipartFile file, String fileName, String bucketName) throws IOException {
        var cps = new ComputingPercentageStream(redisService, file.getInputStream(), file.getSize(), fileName);

        CompletableFuture<?> uploadFileFuture = CompletableFuture.runAsync(() -> {
            try {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(fileName)
                                .contentType(file.getContentType())
                                .stream(cps, file.getSize(), -1)
                                .build());

            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                     XmlParserException e) {

                throw new MinioS3CustomException("Error upon uploading file");
            }
        });

        uploadFileFuture.whenComplete((result, error) -> {
            if (error != null) {
                log.error("Error while adding file: ", error);
            }
            try {
                cps.close();
            } catch (IOException e) {
                log.warn("⚠️ Failed to close stream: {}", e.getMessage());
            }
            log.info("Successfully added file: " + Instant.now());
        });

    }


    private String formName(MultipartFile file) {
        String prefix = file.getContentType() + "/";
        return prefix + UUID.randomUUID() + "-" + file.getOriginalFilename();
    }


}
