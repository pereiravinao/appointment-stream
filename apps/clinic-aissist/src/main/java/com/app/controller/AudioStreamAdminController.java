package com.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.handler.AudioStreamWebSocketHandler;
import com.app.response.AudioStreamSessionStatusResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/audio-stream")
@RequiredArgsConstructor
public class AudioStreamAdminController {

    private final AudioStreamWebSocketHandler audioStreamWebSocketHandler;

    @GetMapping("/sessions")
    public List<AudioStreamSessionStatusResponse> listActiveSessions() {
        return audioStreamWebSocketHandler.listSessions();
    }
}