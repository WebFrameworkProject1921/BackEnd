package com.example.demo.domain;

import lombok.Data;

@Data
public class Comment {
    private long id;
    private String author;
    private long authorId;
    private long postId;
    private String comment;
    private String createDate;
}
