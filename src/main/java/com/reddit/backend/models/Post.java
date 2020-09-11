package com.reddit.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;
    @NotBlank(message = "Post Can't be Empty, Please Fill")
    private String post_name;
    @Nullable
    private String url;
    @Lob
    private String description;
    private Integer vote_count =0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id" , referencedColumnName = "user_Id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subreddit_Id" , referencedColumnName = "subreddit_Id")
    private Subreddit subreddit;

    private Instant createdDate;

    
}
