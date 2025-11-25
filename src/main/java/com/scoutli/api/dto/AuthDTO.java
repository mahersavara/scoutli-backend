package com.scoutli.api.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class AuthDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        public String email;
        public String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        public String email;
        public String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        public String token;
    }
}
