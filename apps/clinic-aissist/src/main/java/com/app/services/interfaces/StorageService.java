package com.app.services.interfaces;

public interface StorageService {
    String generatePresignedUploadUrl(String filename);

    String getPublicUrl(String filename);

    void delete(String fileName);
}
