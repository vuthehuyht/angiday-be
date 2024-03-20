package com.macrace.angidaybe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponseTemplate(
        String message,
        String code,
        @JsonProperty("data") Object data
) {
}
