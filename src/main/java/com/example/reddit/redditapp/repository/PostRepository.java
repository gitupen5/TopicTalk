package com.example.reddit.redditapp.repository;

import com.example.reddit.redditapp.module.Post;
import com.example.reddit.redditapp.module.Subreddit;
import com.example.reddit.redditapp.module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}