package com.example.reddit.redditapp.repository;

import com.example.reddit.redditapp.module.Post;
import com.example.reddit.redditapp.module.User;
import com.example.reddit.redditapp.module.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}