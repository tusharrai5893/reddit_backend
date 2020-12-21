package com.reddit.backend.controllers;

import com.reddit.backend.dto.PostReqDto;
import com.reddit.backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostsController {

    private final PostService postService;

    @PostMapping(value = "/add-posts")
    public ResponseEntity createPost(@RequestBody @Valid PostReqDto postReqDto) {
        postService.persistPost(postReqDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
