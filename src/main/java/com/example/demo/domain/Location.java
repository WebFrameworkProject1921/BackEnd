package com.example.demo.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Location {
    private String name;
    private String lat;
    private String lng;
}