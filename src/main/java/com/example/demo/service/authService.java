package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.repository.KakaoMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;


@Service
@Slf4j
@RequiredArgsConstructor
public class authService {

    private final KakaoMemberRepository kakaoMemberRepository;


    public KakaoTokenDTO getKakaoAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Http Response Body 객체 생성
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "eeff903cae28535861429678e150f740");
        params.add("redirect_uri", "http://localhost:3000/login/oauth2/kakao");
        params.add("code", code);


        // 헤더와 바디 합치기 위해 Http Entity 객체 생성

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 카카오로부터 Access token 받아오기

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KakaoTokenDTO> response = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token", request, KakaoTokenDTO.class);
        KakaoTokenDTO kakaoTokenDto = response.getBody();

        log.info("{}", kakaoTokenDto);

        return kakaoTokenDto;
    }


    public ResponseEntity<KakaoMemberLoginResponseDTO> kakaoLogin(String kakaoAccessToken) {
        //우선 카카오 사용자 정보를 Member 객체에 가지고 온다.
        KakaoMember kakaoMember = getKakaoInfo(kakaoAccessToken);

        KakaoMemberLoginResponseDTO kakaoMemberLoginResponseDTO = new KakaoMemberLoginResponseDTO();
        kakaoMemberLoginResponseDTO.setKakaoMember(kakaoMember);


        KakaoMember findKakaoMember = kakaoMemberRepository.findById(kakaoMember.getId());

        // Member 객체가 이미 Repository에 없다면

        if (findKakaoMember == null) {
            log.info("처음 로그인 하는 사용자입니다. memberRepository에 사용자의 데이터를 저장합니다");
            kakaoMemberRepository.save(kakaoMember);
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

       return new ResponseEntity<>(kakaoMemberLoginResponseDTO, header, HttpStatus.OK);
    }

//kakaoAccessToken을 이용해서 카카오 API 서버로 부터 사용자 정보를 가지고 온다.
   // 사용자의 정보는 Member 객체에 담아서 반환한다.
    public KakaoMember getKakaoInfo(String kakaoAccessToken) {
        KakaoMember kakaoMember = new KakaoMember();

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        HttpEntity<MultiValueMap<String, String>> accountInfoRequest = new HttpEntity<>(headers);


        //카카오로 사용자의 정보를 요청한다.
        ResponseEntity<KakaoAccountDTO> accountInfoResponse = rt.postForEntity(
                "https://kapi.kakao.com/v2/user/me",
                accountInfoRequest,
                KakaoAccountDTO.class
        );

        KakaoAccountDTO kakaoAccountDto = accountInfoResponse.getBody();

        log.info("Kakao Account = {} ", kakaoAccountDto);

        kakaoMember.setId(kakaoAccountDto.getId());
        kakaoMember.setNickname(kakaoAccountDto.getKakao_account().getProfile().getNickname());
        kakaoMember.setEmail(kakaoAccountDto.getKakao_account().getEmail());

        log.info("member = {} ", kakaoMember);

        return kakaoMember;
    }
}
