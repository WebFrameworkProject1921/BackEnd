package com.example.demo.controller;

import com.example.demo.domain.Location;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Setter
@ToString
public class PostForm {
    private Long id;

    private String userName;

    private String memo;

    private Location location;

    private Long authorId;

    private String createDate;

    private List<MultipartFile> imageFiles;



}
