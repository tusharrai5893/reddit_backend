package com.reddit.backend.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.reddit.backend.dto.PostReqDto;
import com.reddit.backend.dto.PostResDto;
import com.reddit.backend.models.Post;
import com.reddit.backend.models.Subreddit;
import com.reddit.backend.models.User;
import com.reddit.backend.repository.CommentRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepo commentRepo;

    @Mapping(target = "user", source = "currentUser")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subredditName")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post mapDTOtoModel(PostReqDto postReqDto, Subreddit subredditName, User currentUser);

    @Mapping(target = "subredditName", source = "post.subreddit.subredditName")
    @Mapping(target = "userName", source = "post.user.username")
    @Mapping(target = "voteCount", source = "post.voteCount")
    @Mapping(target = "commentCount", expression = "java(getCommentCount(post))")
    @Mapping(target = "duration", expression = "java(getPostDuration(post))")
    public abstract PostResDto mapModelToDto(Post post);

    public Integer getCommentCount(Post post){

        Integer size = commentRepo.findByPost(post).size();
        //System.out.print(size);
        return size;
    }

    public String getPostDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
