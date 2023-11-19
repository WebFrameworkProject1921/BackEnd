package com.example.demo.controller;


import com.example.demo.domain.Comment;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {


    private final CommentRepository commentRepository;


    @GetMapping("/{postId}")
    public List<Comment> Comments(@PathVariable long postId) {


        List<Comment> comments = commentRepository.findAll(postId);

        log.info("comment={}", comments);

        return comments;
    }

    @PostMapping
    public String addComment(@RequestBody Comment comment) throws IOException {

        log.info("comment = {}", comment);

        commentRepository.save(comment);

        return "Success";
    }


    @PutMapping("/{commentId}")
    public String updateComment(@PathVariable long commentId, @RequestBody Comment comment) {

        Comment findComment = commentRepository.findById(commentId);

        findComment.setComment(comment.getComment());

        return "Success";
    }

    @DeleteMapping("/{commentId}")
    public String deletePost(@PathVariable long commentId) {

        commentRepository.delete(commentId);

        return "Success";

    }
}
