package com.macrace.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponseTemplate(
        String message,
        String code,
        @JsonProperty("data") Object data
) { }
