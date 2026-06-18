package com.restfulbooker.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO for Authentication
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auth {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public Auth() {
    }

    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static AuthBuilder builder() {
        return new AuthBuilder();
    }

    public static class AuthBuilder {
        private String username;
        private String password;

        public AuthBuilder username(String username) {
            this.username = username;
            return this;
        }

        public AuthBuilder password(String password) {
            this.password = password;
            return this;
        }

        public Auth build() {
            return new Auth(username, password);
        }
    }
}


