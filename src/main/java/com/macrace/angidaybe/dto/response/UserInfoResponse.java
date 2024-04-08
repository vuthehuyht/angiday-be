package com.macrace.angidaybe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserInfoResponse(
        String username,
        @JsonProperty("full_name")
        String fullName
) {
}
