package com.example.demo.controller;

import com.example.demo.domain.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardForm {
    private String id;
    private String boardTitle;
    private int duration;
    private Long authorId;
    private List<Column> columnList;
}