package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository

public class MemberRepository {

    private final Map<Long, Member> store = new HashMap<>();

    public Member findById(Long id) {
        return store.get(id);
    }


    public Member findByIdAndPassword(String loginId, String password) {

        for (Member member : store.values()) {
            if (member.getLoginId().equals(loginId) && member.getPassword().equals(password)) {
                return member;
            }
        }
        return null;
    }

    public void save(Member member) {
        store.put(member.getId(), member);
    }

}
