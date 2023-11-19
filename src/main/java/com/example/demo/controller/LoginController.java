package com.example.demo.controller;

import com.example.demo.domain.MemberLoginResponseDTO;
import com.example.demo.domain.Member;
import com.example.demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@CrossOrigin(origins = "*")


public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDTO> login(@RequestBody Member member) {

        return loginService.login(member);
    }


    @PostMapping("/signup")
    public ResponseEntity<MemberLoginResponseDTO> signup(@RequestBody Member member) {

        return loginService.signup(member);
    }
}
