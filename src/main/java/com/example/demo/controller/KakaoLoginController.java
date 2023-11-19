package com.example.demo.controller;


import com.example.demo.domain.KakaoMemberLoginResponseDTO;
import com.example.demo.service.authService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class KakaoLoginController {

    private final authService authService;


    @GetMapping("/login/oauth2/kakao")
    public ResponseEntity<KakaoMemberLoginResponseDTO> kakaoLogin(HttpServletRequest request) {
        String code = request.getParameter("code"); //사용자가 준 인가 코드를 받아온다.

        String kakaoAccessToken = authService.getKakaoAccessToken(code).getAccessToken(); //인가코드를 카카오 서버에 보내고 토큰을 받아온다.

        return authService.kakaoLogin(kakaoAccessToken);
    }


}
