package com.example.demo.domain;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String loginId;
    private String email;
    private String nickname;
    private String password;
}
