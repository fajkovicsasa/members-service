package com.example.memberservice.controller;

import com.example.memberservice.DTO.MemberDTO;
import com.example.memberservice.exception.MemberNotFoundException;
import com.example.memberservice.model.Member;
import com.example.memberservice.service.MemberService;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    private MemberService memberService;
    private ModelMapper modelMapper;

    public MemberController(MemberService memberService, ModelMapper modelMapper) {
        this.memberService = memberService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Member>> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        log.info("Received request to return all members.");

        return new ResponseEntity<>(memberService.getAll(page, size), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDTO> getOne(@PathVariable @NonNull Long id) {
        log.info("Received request to get member details for id {}", id);

        try {
            Optional<Member> memberOptional = memberService.getOne(id);
            return memberOptional.isPresent() ? new ResponseEntity<>(modelMapper.map(memberOptional.get(), MemberDTO.class), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity addMember(@NonNull @RequestBody MemberDTO memberDTO) {
        log.info("Received request to add new member: {}", memberDTO);

        try {
            memberService.addNew(modelMapper.map(memberDTO, Member.class));
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        log.info("Received request to update member: {}", id);

        Member member = modelMapper.map(memberDTO, Member.class);
        member.setId(id);

        try {
            memberService.update(member);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity deleteAllInactive(@RequestParam boolean deleteAllInactive) {
        log.info("Received request to delete all inactive members.");

        if (deleteAllInactive) {
            memberService.deleteAllInactive();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
