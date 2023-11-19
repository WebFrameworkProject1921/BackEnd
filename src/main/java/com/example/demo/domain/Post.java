package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Post {

    private Long id;

    private String userName;

    private String memo;

    private Long authorId;

    private Location location;

    private String createDate;

    private List<UploadFile> imageFiles;


}
