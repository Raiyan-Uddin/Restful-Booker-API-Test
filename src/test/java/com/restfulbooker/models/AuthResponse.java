package com.restfulbooker.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO for Authentication Response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("reason")
    private String reason;

    public AuthResponse() {
    }

    public AuthResponse(String token, String reason) {
        this.token = token;
        this.reason = reason;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
        private String token;
        private String reason;

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponseBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(token, reason);
        }
    }
}


