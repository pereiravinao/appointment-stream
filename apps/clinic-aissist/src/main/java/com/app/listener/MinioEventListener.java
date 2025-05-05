package com.app.listener;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.app.factory.TranscriptionFactory;
import com.app.repository.TranscriptionRepository;
import com.app.services.interfaces.StorageEventService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioEventListener {

    private final TranscriptionFactory transcriptionFactory;
    private final TranscriptionRepository transcriptionRepository;
    private final StorageEventService storageEventService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${spring.rabbitmq.name}")
    public void handleMinioEvent(String messageBody) {
        log.info("handleMinioEvent: {}", messageBody);
        try {
            var event = objectMapper.readValue(messageBody, S3EventNotification.class);

            for (S3EventNotification.S3EventNotificationRecord record : event.getRecords()) {
                String key = URLDecoder.decode(record.getS3().getObject().getKey(), StandardCharsets.UTF_8);
                if (key.contains("chunk")) {
                    var sessionId = extractSessionId(key);
                    storageEventService.processChunkAudio(sessionId, key);
                }
            }
        } catch (Exception ex) {
            log.error("Erro ao processar evento MinIO no RabbitMQ", ex);
        }
    }

    private String extractSessionId(String key) {
        return key.split("session/")[1].split("/")[0];
    }

}
