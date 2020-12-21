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

    private long subreddit_id;
    private String subreddit_Name;
    private String subreddit_Description;
    private Integer NoOfPosts;
}
