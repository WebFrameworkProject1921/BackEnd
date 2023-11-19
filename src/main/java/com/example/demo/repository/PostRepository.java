package com.example.demo.repository;


import com.example.demo.domain.Post;
import com.example.demo.domain.UploadFile;
import com.example.demo.file.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final Map<Long, Post> store = new HashMap<>();

    private final FileStore fileStore;

    private long sequence = 0L;


    public Post findById(Long id) {
        return store.get(id);
    }

    public Post save(Post post) {
        post.setId(++sequence);
        store.put(post.getId(), post);
        return post;
    }


    public List<Post> findAll() {

        return new ArrayList<>(store.values());
    }


    public void delete(Long PostId) {
        Post findPost = findById(PostId);

        for(UploadFile uploadFile : findPost.getImageFiles()) {
            fileStore.deleteFile(uploadFile.getStoreFileName());

        }


        store.remove(findPost.getId());
    }

}
