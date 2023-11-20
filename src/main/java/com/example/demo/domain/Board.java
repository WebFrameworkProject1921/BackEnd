package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Board {
    private String id;
    private String boardTitle;
    private int duration;
    private Long authorId;
    private List<Column> columnList;
}




