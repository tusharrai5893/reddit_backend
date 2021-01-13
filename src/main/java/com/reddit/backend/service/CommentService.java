package com.reddit.backend.service;


import com.reddit.backend.dto.CommentDto;
import com.reddit.backend.exceptions.RedditCustomException;
import com.reddit.backend.mailConfig.CustomMailContentBuilder;
import com.reddit.backend.mailConfig.MailService;
import com.reddit.backend.mailConfig.NotificationEmail;
import com.reddit.backend.mapper.CommentMapper;
import com.reddit.backend.models.Comment;
import com.reddit.backend.models.Post;
import com.reddit.backend.models.User;
import com.reddit.backend.repository.CommentRepo;
import com.reddit.backend.repository.PostRepo;
import com.reddit.backend.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    private final AuthService authService;
    private final CustomMailContentBuilder customMailContentBuilder;
    private final MailService mailService;

    private final CommentMapper commentMapper;


    public void createComment(CommentDto commentDto) {

        Post commentOnPost = postRepo.findById(commentDto.getPostId())
                .orElseThrow(() -> new RedditCustomException("No post found by ID= "+ commentDto.getPostId().toString()));

        User currentUser = authService.getCurrentUser();
        Comment comment = commentMapper.mapDTOtoModel(commentDto, commentOnPost, currentUser);
        commentRepo.save(comment);

        NotificationEmail.builder()
        .subject(commentOnPost.getUser().getUsername() + "commented on a post")
        .recipient(commentOnPost.getUser().getEmail())
        .body(currentUser +"posted a comment on your post !")
        .link("")
        .msg("");

    }
}
