package com.app.services.interfaces;

public interface StorageEventService {

    void processChunkAudio(String sessionId, String storageKey);

    void processFullAudio(String sessionId);
}
