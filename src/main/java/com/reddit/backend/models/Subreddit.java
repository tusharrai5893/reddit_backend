package com.reddit.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subreddit_id;
    @NotBlank(message = "Subreddit name is required")
    private String subreddit_Name;
    @NotBlank(message = "Subreddit Description is Required")
    private String subreddit_Description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", referencedColumnName = "user_Id")
    private User user;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;

    private Instant createdDate;


}
