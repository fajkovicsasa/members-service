package com.example.memberservice.service.impl;

import com.example.memberservice.exception.MemberNotFoundException;
import com.example.memberservice.model.Member;
import com.example.memberservice.repository.MemberRepository;
import com.example.memberservice.service.MemberService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    private final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Add new member to the database
     * @param member
     */
    @Override
    public void addNew(@NonNull Member member) {
        log.debug("Adding new member: {}", member);
        if (member.getId() != null) {
            throw new IllegalStateException("Member ID must be null when adding new member.");
        }
        memberRepository.save(member);
    }

    /**
     * Retrieve a single member based on the provided id
     * @param id
     * @return
     */
    @Override
    public Optional<Member> getOne(@NonNull Long id) {
        log.debug("Retrieving member with id {}", id);
        return memberRepository.findById(id);
    }

    /**
     * Retrieve a set of members from the database based on the page
     * (starting from 0 as 1st page) and size (number of members)
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<Member> getAll(int page, int size) {
        log.debug("Retrieving all members. Page: {}, size: {}", page, size);
        return (List<Member>) memberRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending())).getContent();
    }

    /**
     * Update user details
     * @param member
     */
    @Override
    public void update(@NonNull Member member) {
        log.debug("Updating member: {}", member);
        if (member.getId() == null) {
            throw new IllegalStateException("Member ID must be defined.");
        }
        if (!getOne(member.getId()).isPresent()) {
            throw new MemberNotFoundException(member.getId());
        }
        memberRepository.save(member);
    }

    /**
     * Remove all inactive users from the database
     */
    @Override
    public void deleteAllInactive() {
        log.debug("Deleting all inactive members.");
        memberRepository.removeAllInactive();
    }
}
