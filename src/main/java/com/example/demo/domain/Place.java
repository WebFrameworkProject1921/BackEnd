package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Place {
    private String name;
    private String address;
    private String category;
    private String categoryDetail;
    private String phone;
    private String lat;
    private String lng;
}
