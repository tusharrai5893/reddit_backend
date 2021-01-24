package com.reddit.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subreddit implements Serializable {

    //non-owning side of relationship
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subreddit")
    @JsonIgnore
    public List<Post> posts;
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
    private Instant createdDate;


}
