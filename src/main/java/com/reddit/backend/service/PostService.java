package com.reddit.backend.service;

import com.reddit.backend.dto.PostReqDto;
import com.reddit.backend.repository.PostRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepo;

    public void persistPost(PostReqDto postReqDto) {


    }
}
