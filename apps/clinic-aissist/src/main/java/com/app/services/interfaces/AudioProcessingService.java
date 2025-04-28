package com.app.services.interfaces;

import java.util.List;

public interface AudioProcessingService {
    byte[] concatenateAudios(List<String> audioUrls);
}
