package com.example.demo.repository;

import com.example.demo.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class CommentRepository {


    private final Map<Long, Comment> store = new HashMap<>();
    private long sequence = 0L;


    public Comment findById(Long id) {
        return store.get(id);
    }

    public Comment save(Comment comment) {
        comment.setId(++sequence);
        store.put(comment.getId(), comment);
        return comment;
    }


    public List<Comment> findAll(long postId) {
        return store.values().stream()
                .filter(comment -> comment.getPostId() == postId)
                .collect(Collectors.toList());
    }


    public void delete(Long commnetId) {
        Comment findComment = findById(commnetId);

        store.remove(findComment.getId());
    }
}
