package com.reddit.backend.mapper;

import com.reddit.backend.dto.PostReqDto;
import com.reddit.backend.dto.PostResDto;
import com.reddit.backend.models.Post;
import com.reddit.backend.models.Subreddit;
import com.reddit.backend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "user", source = "currentUser")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subredditName")
    Post mapDTOtoModel(PostReqDto postReqDto, Subreddit subredditName, User currentUser);

    @Mapping(target = "subredditName", source = "post.subreddit.subredditName")
    @Mapping(target = "userName", source = "post.user.username")
    PostResDto mapModelToDto(Post post);
}
