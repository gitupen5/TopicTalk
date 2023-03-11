package com.example.reddit.redditapp.controller;

import com.example.reddit.redditapp.dto.PostRequest;
import com.example.reddit.redditapp.dto.PostResponse;
import com.example.reddit.redditapp.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    //Create Post
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody @Valid PostRequest postRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Invalid request body");
        }
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id) {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername( String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }
}