package com.app.services.interfaces;

public interface StorageService {
    String uploadAudio(byte[] file, String fileName);

    void delete(String fileName);
}
