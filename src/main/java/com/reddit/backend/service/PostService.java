package com.reddit.backend.service;

import com.reddit.backend.dto.PostReqDto;
import com.reddit.backend.exceptions.RedditCustomException;
import com.reddit.backend.mapper.PostMapper;
import com.reddit.backend.models.Post;
import com.reddit.backend.models.Subreddit;
import com.reddit.backend.repository.PostRepo;
import com.reddit.backend.repository.SubredditRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final SubredditRepo subredditRepo;

    private final AuthService authService;

    private final PostMapper postMapper;

    public Post persistPost(PostReqDto postReqDto) {

        Subreddit subredditName = subredditRepo
                .findByName(postReqDto.getSubreddit_Name())
                .orElseThrow(()-> new RedditCustomException(postReqDto.getSubreddit_Name()));

        return postRepo.save(postMapper.map(postReqDto,subredditName, authService.getCurrentUser()));

    }


}

