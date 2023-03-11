package com.example.reddit.redditapp.mapper;

import com.example.reddit.redditapp.dto.PostRequest;
import com.example.reddit.redditapp.dto.PostResponse;
import com.example.reddit.redditapp.module.Post;
import com.example.reddit.redditapp.module.Subreddit;
import com.example.reddit.redditapp.module.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface PostMapper {


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
//    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "userName", expression = "java(post.getUser() != null ? post.getUser().getUsername() : null)")

    PostResponse mapToDto(Post post);
}
