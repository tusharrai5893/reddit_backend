package com.reddit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentDto {

    private Long commentId;
    private Long postId;
    private Instant createdDate;
    private String commentText;
    private String commentedUsername;

}
