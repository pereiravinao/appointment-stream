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
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${minio.storage.bucket}")
    private String bucketName;

    @Override
    public String uploadAudio(byte[] audioData, String filename) {
        try {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .contentType("audio/wav")
                    .build();

            s3Client.putObject(objectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(audioData));

            // Gera a URL pública assinada (expira em 7 dias, ajuste se quiser)
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofDays(7))
                    .getObjectRequest(r -> r.bucket(bucketName).key(filename))
                    .build();

            URL url = s3Presigner.presignGetObject(presignRequest).url();
            log.info("URL do arquivo salvo: {}", url.toString());
            return url.toString();

        } catch (S3Exception e) {
            log.error("Erro ao fazer upload no storage: {}", e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Erro ao fazer upload no storage: " + e.awsErrorDetails().errorMessage());
        }
    }

    @Override
    public void delete(String fileName) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(fileName).build());
        log.info("Arquivo deletado com sucesso: {}", fileName);
    }

}