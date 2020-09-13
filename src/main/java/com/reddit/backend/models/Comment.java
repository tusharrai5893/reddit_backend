package com.reddit.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_Id;
    @NotEmpty
    private String comment_Text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_Id" , referencedColumnName = "post_Id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id" , referencedColumnName = "user_Id")
    private User user;
    private Instant createdDate;

}
