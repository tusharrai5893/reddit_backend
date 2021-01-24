package com.reddit.backend.dto;

import com.reddit.backend.models.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {

    private long subredditId;
    private String subredditName;
    private String subredditDescription;
    private List<Post> NoOfPosts;
}
