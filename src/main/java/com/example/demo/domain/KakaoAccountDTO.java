package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class KakaoAccountDTO {


    @JsonProperty("id")

    private Long id;

    @JsonProperty("kakao_account")

    private KakaoAccount kakao_account;


}
