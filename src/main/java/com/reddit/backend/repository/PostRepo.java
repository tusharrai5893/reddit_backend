package com.reddit.backend.repository;

import com.reddit.backend.models.Post;
import com.reddit.backend.models.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);
}
