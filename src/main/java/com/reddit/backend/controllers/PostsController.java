package com.reddit.backend.controllers;

import com.reddit.backend.dto.PostReqDto;
import com.reddit.backend.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private final PostService postService;

    public PostsController(PostService postService) {
        this.postService = postService;
    }

    public ResponseEntity createPost(@RequestBody @Valid PostReqDto postReqDto) {
        postService.persistPost(postReqDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
