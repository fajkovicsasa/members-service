package com.example.memberservice.service;

import com.example.memberservice.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    void addNew(Member member);

    Optional<Member> getOne(Long id);

    List<Member> getAll(int page, int size);

    void update(Member member);

    void deleteAllInactive();

}
