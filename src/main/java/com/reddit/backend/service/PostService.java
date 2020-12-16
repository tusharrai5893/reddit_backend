package com.reddit.backend.service;

import com.reddit.backend.dto.PostReqDto;
import com.reddit.backend.repository.PostRepo;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepo postRepo;

    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public void persistPost(PostReqDto postReqDto) {


    }
}
