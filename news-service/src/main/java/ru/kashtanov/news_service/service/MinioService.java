package ru.kashtanov.news_service.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.kashtanov.news_service.config.ComputingPercentageStream;
import ru.kashtanov.news_service.exceptions.MinioS3CustomException;
import ru.kashtanov.news_service.inits.BucketInitializer;
import ru.kashtanov.news_service.repo.ContentRepo;

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
    private final ContentRepo contentRepo;


    public MinioService(MinioClient minioClient, BucketInitializer bucketInitializr, RedisService redisService, ContentRepo contentRepo) {
        this.minioClient = minioClient;
        this.bucketInitializr = bucketInitializr;
        this.redisService = redisService;
        this.contentRepo = contentRepo;
    }

    @Transactional
    public String uploadMedia(MultipartFile file) {
        if (file == null || file.getSize() <= 0) {
            throw new MinioS3CustomException("File can't be processed since it is empty");
        }
        String fileName = formName(file);
        String bucketName = bucketInitializr.getBucketName();
        try {
            addMedia(file, fileName, bucketName);
        } catch (IOException e) {
            throw new MinioS3CustomException("Impossible to add file");
        }
        return fileName;

    }

    @Transactional
    public void addMedia(MultipartFile file, String fileName, String bucketName) throws IOException {
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
                contentRepo.deleteByStoredFilename(fileName);
                log.error("Error while adding file: ", error);
            }
            try {
                cps.close();
            } catch (IOException e) {
                log.warn("Failed to close stream: {}", e.getMessage());
            }
            log.info("Successfully added file: " + Instant.now());
        });

    }

    public String getLinkForContent(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketInitializr.getBucketName())
                            .object(fileName)
                            .expiry(1, TimeUnit.DAYS)
                            .method(Method.GET)
                            .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException |
                 ServerException e) {

            throw new MinioS3CustomException("Error while getting presigned object url");
        }

    }


    private String formName(MultipartFile file) {
        String prefix = file.getContentType() + "/";
        return prefix + UUID.randomUUID() + "-" + file.getOriginalFilename();
    }


}
