package com.example.reddit.redditapp.exceptions;
public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String message) {
        super(message);
    }
}

