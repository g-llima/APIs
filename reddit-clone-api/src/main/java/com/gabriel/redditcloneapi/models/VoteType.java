package com.gabriel.redditcloneapi.models;

public enum VoteType {

    UPVOTE(1),
    DOWNVOTE(-1),;

    VoteType(int direction){}
}
