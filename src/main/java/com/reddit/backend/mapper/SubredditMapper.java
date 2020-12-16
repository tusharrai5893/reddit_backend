package com.reddit.backend.mapper;

import com.reddit.backend.dto.SubredditDto;
import com.reddit.backend.models.Post;
import com.reddit.backend.models.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "NoOfPosts", expression = "java(mapModelNoOfPosts(subreddit.getPosts()))")
    SubredditDto mapModelToDTO(Subreddit subreddit);

    default Integer mapModelNoOfPosts(List<Post> posts) {
        return posts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDTOToModel(SubredditDto subredditDto);
}

