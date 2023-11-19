package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class KakaoAccount {

    @JsonProperty("email")
    String email;

    @JsonProperty("profile")
    Profile profile;
}
