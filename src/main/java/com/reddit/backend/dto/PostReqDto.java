package com.reddit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostReqDto {

    private Long postId;
    private String subreddit_Name;
    private String post_Name;
    private String url;
    private String post_Description;


}
