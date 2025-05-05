package com.app.response;

import lombok.Data;

@Data
public class GroqResponse {

    private String text;
    private XGroq xGroq;

    @Data
    public static class XGroq {
        private String id;
    }
}
