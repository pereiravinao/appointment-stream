package com.app.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum WebSocketMessageType {
    GET_UPLOAD_URL("getUploadUrl"),
    UPLOAD_URL_RESPONSE("uploadUrlResponse"),
    END_SESSION("endSession"),
    ERROR("error");

    private final String value;

    WebSocketMessageType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static WebSocketMessageType fromValue(String value) {
        return Arrays.stream(values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo inválido: " + value));
    }
}
