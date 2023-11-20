package com.example.demo.repository;

import com.example.demo.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final Map<String, Board> store = new HashMap<>();

    private long sequence = 0L;

    public Board findById(String id) {
        return store.get(id);
    }

    public Board save(Board board) {
        store.put(board.getId(), board);
        return board;
    }

    public List<Board> findAll(Long authorId) {
        List<Board> result = new ArrayList<>();
        for (Board board : store.values()) {
            if (board.getAuthorId() != null && board.getAuthorId().equals(authorId)) {
                result.add(board);
            }
        }
        return result;
    }

    public void delete(String BoardId) {
        Board findPost = findById(BoardId);
        store.remove(findPost.getId());
    }
}
