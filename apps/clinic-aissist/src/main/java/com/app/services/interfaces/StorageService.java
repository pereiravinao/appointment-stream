package com.app.services.interfaces;

public interface StorageService {
    String generatePresignedUploadUrl(String filename);

    String getPublicUrl(String filename);

    String getSignedUrl(String key);

    void delete(String fileName);
    
    String concatAudioChunks(String sessionId);
}
