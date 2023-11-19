package com.example.demo.controller;

import com.example.demo.domain.Post;
import com.example.demo.domain.UploadFile;
import com.example.demo.file.FileStore;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final FileStore fileStore;


    @GetMapping
    public List<Post> Posts() {

        List<Post> posts = postRepository.findAll();

        log.info("posts={}", posts);

        return posts;


    }

    @PostMapping
    public String addPost(@ModelAttribute PostForm postForm) throws IOException {



        List<UploadFile> storeImagesFiles = fileStore.storeFile(postForm.getImageFiles());

        //데이터베이스에 저장

        Post post = new Post();

        post.setMemo(postForm.getMemo());
        post.setUserName(postForm.getUserName());
        post.setAuthorId(postForm.getAuthorId());
        post.setLocation(postForm.getLocation());
        post.setCreateDate(postForm.getCreateDate());
        post.setImageFiles(storeImagesFiles);

        postRepository.save(post);


        log.info("postForm={}", postForm);

        log.info("post={}", post);


        return "Success";
    }

    @PutMapping("/{postId}")
    public String updatePost(@PathVariable long postId, @ModelAttribute PostForm postForm, @RequestPart("serverImages") List<UploadFile> serverImages) throws IOException {
        List<UploadFile> storeImagesFiles = null;
        Post post = postRepository.findById(postId);

        if(postForm.getImageFiles() !=  null) {
            storeImagesFiles = fileStore.storeFile(postForm.getImageFiles()); //새로운 이미지 저장
        }



            List<UploadFile> imageFiles = post.getImageFiles();


            imageFiles.removeIf(imageFile -> {
                boolean isAbsentOnServer = serverImages.stream().noneMatch(serverImage -> serverImage.getStoreFileName().equals(imageFile.getStoreFileName()));
                if (isAbsentOnServer) {
                    fileStore.deleteFile(imageFile.getStoreFileName());
                }
                return isAbsentOnServer;
            });


        if (storeImagesFiles != null) {
            imageFiles.addAll(storeImagesFiles);
        }


        post.setMemo(postForm.getMemo());
        post.setLocation(postForm.getLocation());

        return "Success";
    }




    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable long postId) {


        postRepository.delete(postId);

        return "Success";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {

        String fullPath = fileStore.getFullPath(filename);


       return new UrlResource("file:" + fullPath);
    }

}