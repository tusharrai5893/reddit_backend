package com.reddit.backend.mapper;

import com.reddit.backend.dto.PostReqDto;
import com.reddit.backend.models.Post;
import com.reddit.backend.models.Subreddit;
import com.reddit.backend.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post map(PostReqDto postReqDto, Subreddit subredditName, User currentUser);
}
