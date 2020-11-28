package com.reddit.backend.repository;

import com.reddit.backend.models.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepo extends JpaRepository<Subreddit, Long> {
}
