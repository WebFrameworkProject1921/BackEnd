package com.example.demo.repository;


import com.example.demo.domain.KakaoMember;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class KakaoMemberRepository {

    private final Map<Long, KakaoMember> store = new HashMap<>();

    public KakaoMember findById(Long id) {
        return store.get(id);
    }


    public void save(KakaoMember kakaoMember) {
        store.put(kakaoMember.getId(), kakaoMember);
    }

}
