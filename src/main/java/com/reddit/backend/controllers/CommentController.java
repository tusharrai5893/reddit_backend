package com.reddit.backend.controllers;

import com.reddit.backend.dto.CommentDto;
import com.reddit.backend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
@RequestMapping("api/comment/")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add-comment")
    public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto){

        commentService.createComment(commentDto);

        return status(201).body("");
    }
}
