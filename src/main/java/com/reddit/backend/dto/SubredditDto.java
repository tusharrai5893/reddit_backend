package com.reddit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {

    private long subredditId;
    private String subredditName;
    private String subredditDescription;
    private Integer NoOfPosts;
}
