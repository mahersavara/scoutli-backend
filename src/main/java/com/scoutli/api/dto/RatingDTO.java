package com.scoutli.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    public Long id;
    public int score;
    public String userEmail;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        public int score;
    }
}
