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
    private long subreddit_Id;
    @NotBlank(message = "Community Name Can't be Empty")
    private String community_Name;
    @NotBlank(message = "Subreddit Description is Required")
    private String description;
    private Instant created_Date;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;


}
