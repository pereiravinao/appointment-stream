package com.app.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.properties.MinioStorageProperties;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {

    private final MinioStorageProperties properties;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(properties.getEndpoint()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())))
                .region(Region.of(properties.getRegion()))
                .httpClientBuilder(ApacheHttpClient.builder()
                        .tlsTrustManagersProvider(TrustAllTlsTrustManagerProvider.create()) // <<<<<<
                )
                .serviceConfiguration(
                        S3Configuration.builder()
                                .pathStyleAccessEnabled(true)
                                .build())
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .endpointOverride(URI.create(properties.getEndpoint()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())))
                .region(Region.of(properties.getRegion()))
                .serviceConfiguration(
                        software.amazon.awssdk.services.s3.S3Configuration.builder()
                                .pathStyleAccessEnabled(true)
                                .build())
                .build();
    }

}