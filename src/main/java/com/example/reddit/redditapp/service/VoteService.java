package com.example.reddit.redditapp.service;

import com.example.reddit.redditapp.dto.VoteDto;
import com.example.reddit.redditapp.exceptions.PostNotFoundException;
import com.example.reddit.redditapp.exceptions.SpringRedditException;
import com.example.reddit.redditapp.module.Post;
import com.example.reddit.redditapp.module.Vote;
import com.example.reddit.redditapp.repository.PostRepository;
import com.example.reddit.redditapp.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.reddit.redditapp.module.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            int newVoteCount = (post.getVoteCount() != null) ? post.getVoteCount() + 1 : 1;
            post.setVoteCount(newVoteCount);
        } else {
            int newVoteCount = (post.getVoteCount() != null) ? post.getVoteCount() - 1 : -1;
            post.setVoteCount(newVoteCount);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
