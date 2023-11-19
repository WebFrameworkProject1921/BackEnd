package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.domain.MemberLoginResponseDTO;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public ResponseEntity<MemberLoginResponseDTO> login(Member member) {
        Member findMember = memberRepository.findByIdAndPassword(member.getLoginId(), member.getPassword());

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if (findMember == null) {
            log.info("로그인에 실패한 사용자입니다. memberId가 유효하지 않습니다");
            return new ResponseEntity<>(null, header, HttpStatus.UNAUTHORIZED);
        }

        MemberLoginResponseDTO memberLoginResponseDto = new MemberLoginResponseDTO();
        memberLoginResponseDto.setMember(findMember);

        log.info("로그인에 성공한 사용자입니다.");
        return new ResponseEntity<>(memberLoginResponseDto, header, HttpStatus.OK);
    }


    public ResponseEntity<MemberLoginResponseDTO> signup(Member member) {

        Random random = new Random();

        member.setId(random.nextLong());

        memberRepository.save(member);

        MemberLoginResponseDTO memberLoginResponseDto = new MemberLoginResponseDTO();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        memberLoginResponseDto.setMember(member);

        log.info("로그인에 성공한 사용자입니다.");
        return new ResponseEntity<>(memberLoginResponseDto, header, HttpStatus.OK);
    }
}
