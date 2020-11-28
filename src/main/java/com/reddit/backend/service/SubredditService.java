package com.reddit.backend.service;

import com.reddit.backend.dto.SubredditDto;
import com.reddit.backend.models.Subreddit;
import com.reddit.backend.repository.SubredditRepo;
import com.reddit.backend.security.JwtProviderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepo subredditRepo;
    private final JwtProviderService jwtProviderService;

    @Transactional
    public SubredditDto persistSubreddit(SubredditDto subredditDto) {

        Subreddit persistedSubReddit = subredditRepo.save(mapModelSubredditToDtoPersist(subredditDto));
        subredditDto.setSubreddit_id(persistedSubReddit.getSubreddit_id());

        return subredditDto;


    }

    private Subreddit mapModelSubredditToDtoPersist(SubredditDto dto) {
        return Subreddit.builder()
                .subreddit_Name(dto.getSubreddit_Name())
                .subreddit_Description(dto.getSubreddit_Description())
                .createdDate(Instant.now())
                .build();
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> fetchAllSubreddit() {
        return subredditRepo.findAll()
                .stream()
                .map(this::mapModelSubredditToDtoFetch)
                .collect(Collectors.toList());

    }

    private SubredditDto mapModelSubredditToDtoFetch(Subreddit subreddit) {
        return SubredditDto.builder()
                .subreddit_id(subreddit.getSubreddit_id())
                .subreddit_Name(subreddit.getSubreddit_Name())
                .subreddit_Description(subreddit.getSubreddit_Description())
                .NoOfPosts(subreddit.getPosts().size())
                .build();
    }
}
