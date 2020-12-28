package com.reddit.backend.service;

import com.reddit.backend.dto.PostReqDto;
import com.reddit.backend.dto.PostResDto;
import com.reddit.backend.exceptions.RedditCustomException;
import com.reddit.backend.mapper.PostMapper;
import com.reddit.backend.models.Post;
import com.reddit.backend.models.Subreddit;
import com.reddit.backend.repository.PostRepo;
import com.reddit.backend.repository.SubredditRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PostService {

    private final PostRepo postRepo;
    private final SubredditRepo subredditRepo;

    private final AuthService authService;

    private final PostMapper postMapper;

    public Post persistPost(PostReqDto postReqDto) {
        Subreddit subredditName = subredditRepo
                .findBySubredditName(postReqDto.getSubredditName())
                .orElseThrow(()-> new RedditCustomException("No subreddit named " + postReqDto.getSubredditName()));

        return postRepo.save(postMapper.mapDTOtoModel(postReqDto,subredditName, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public List<PostResDto> fetchAllPost() {
        return postRepo.findAll()
                .stream().map(postMapper::mapModelToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public PostResDto fetchOnePost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RedditCustomException(new StringBuilder().append("No post found for the id=").append(postId).toString()));

       return postMapper.mapModelToDto(post);
    }

    public List<PostResDto> fetchPostBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepo.findById(subredditId)
                .orElseThrow(() -> new RedditCustomException("Unable to fetch Subreddit by subreddit Id= " + subredditId));

        List<Post> subredditbyPost = postRepo.findAllBySubreddit(subreddit);

        return subredditbyPost.stream()
                .map(postMapper::mapModelToDto)
                .collect(toList());


    }
}

