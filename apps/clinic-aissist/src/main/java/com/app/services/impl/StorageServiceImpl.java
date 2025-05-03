package com.app.services.impl;

import java.net.URL;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.services.interfaces.StorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${minio.storage.bucket}")
    private String bucketName;

    @Value("${minio.storage.endpoint}")
    private String endpoint;

    @Override
    public void delete(String fileName) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(fileName).build());
        log.info("Arquivo deletado com sucesso: {}", fileName);
    }

    @Override
    public String generatePresignedUploadUrl(String objectName) {
        try {
            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(60)) // 6 minutos
                    .putObjectRequest(req -> req
                            .bucket(bucketName)
                            .key(objectName)
                            .build())
                    .build();

            URL url = s3Presigner.presignPutObject(presignRequest).url();
            log.info("URL de upload pré-assinada gerada: {}", url);
            return url.toString();
        } catch (Exception e) {
            log.error("Erro ao gerar presigned URL para upload", e);
            throw new RuntimeException("Erro ao gerar presigned URL", e);
        }
    }

    @Override
    public String getPublicUrl(String objectName) {
        return endpoint + "/" + bucketName + "/" + objectName;
    }
}