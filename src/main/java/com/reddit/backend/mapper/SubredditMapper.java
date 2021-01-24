package com.reddit.backend.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reddit.backend.dto.SubredditDto;
import com.reddit.backend.models.Post;
import com.reddit.backend.models.Subreddit;
import com.reddit.backend.models.User;
import com.reddit.backend.service.AuthService;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public interface SubredditMapper {

    AuthService authService = null;

    @Mapping(target = "NoOfPosts", expression = "java(postList(subreddit.getPosts()))")
    SubredditDto mapModelToDTO(Subreddit subreddit);

    default List<Post> postList(List<Post> posts) {

        List<Post> p = new ArrayList<>();
        p.addAll(posts);
        return p;
    }


    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "currentUser")
    Subreddit mapDTOToModel(SubredditDto subredditDto, User currentUser);


}

