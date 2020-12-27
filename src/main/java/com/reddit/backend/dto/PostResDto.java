package com.reddit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResDto {

    private String postId;
    private String postDescription;
    private String url;
    private String postName;
    private String userName;
    private String subredditName;
}
