package com.reddit.backend.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subredditId;

    @NotBlank(message = "Subreddit name is required")
    private String subredditName;

    @NotBlank(message = "Subreddit Description is Required")
    private String subredditDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    //non-owning side of relationship
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "subreddit")
    public List<Post> posts;

    private Instant createdDate;


}
