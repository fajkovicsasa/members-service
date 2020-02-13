package com.example.memberservice.service;

import com.example.memberservice.exception.MemberNotFoundException;
import com.example.memberservice.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void shouldRetrieveOneMember() {
        assertEquals(true, memberService.getOne(1L).isPresent());
        assertEquals(true, memberService.getOne(2L).isPresent());
        assertEquals(true, memberService.getOne(3L).isPresent());
        assertEquals(true, memberService.getOne(4L).isPresent());
        assertEquals(true, memberService.getOne(5L).isPresent());
    }

    @Test
    public void shouldReturnEmptyOptional() {
        assertFalse(memberService.getOne(0L).isPresent());
    }

    @Test
    public void shouldReturnAllMembersFromDatabase() {
        assertEquals(5, memberService.getAll(0, 100).size());
    }

    @Test
    public void shouldReturn2ResultsFromPage1() {
        assertEquals(2, memberService.getAll(0, 2).size());
    }

    @Test
    public void shouldReturn2ResultsFromPage2() {
        assertEquals(1, memberService.getAll(2, 2).size());
    }

    @Test
    @Rollback
    public void shouldAddNewMember() {
        Member member = new Member(null, "A", "B", LocalDate.now(), null, true);

        int sizeBefore = memberService.getAll(0, Integer.MAX_VALUE).size();

        memberService.addNew(member);
        int sizeAfter = memberService.getAll(0, Integer.MAX_VALUE).size();
        assertTrue(sizeAfter > sizeBefore);
    }

    @Test
    public void shouldThrowExceptionWhenAddingMemberWithPredefinedId() {
        Member member = new Member(1L, "A", "B", LocalDate.now(), null, true);

        Throwable throwable = assertThrows(IllegalStateException.class, () -> {
            memberService.addNew(member);
        });

        assertEquals("Member ID must be null when adding new member.", throwable.getMessage());
    }

    @Test
    @Rollback
    public void shouldUpdateMember() {
        Member member = memberService.getOne(1L).get();
        assertTrue(member.isActive());

        member.setActive(false);
        memberService.update(member);

        member = memberService.getOne(1L).get();
        assertFalse(member.isActive());
    }

    @Test
    public void shoudThrowExceptionWhenIdNotProvidedOnMemberUpdate() {
        Throwable throwable = assertThrows(IllegalStateException.class, () -> {
            memberService.update(new Member(null, "", "", null, null, false));
        });

        assertEquals("Member ID must be defined.", throwable.getMessage());
    }

    @Test
    public void shouldThrowMemberNotFoundException() {
        Throwable throwable = assertThrows(MemberNotFoundException.class, () -> {
            memberService.update(new Member(0L, "", "", null, null, false));
        });

        assertEquals("Member with id 0 does not exist.", throwable.getMessage());
    }

    @Test
    @Rollback
    public void shouldRemoveAllInactiveMembers() {
        int sizeBefore = memberService.getAll(0, Integer.MAX_VALUE).size();
        assertEquals(5, sizeBefore);

        memberService.deleteAllInactive();

        int sizeAfter = memberService.getAll(0, Integer.MAX_VALUE).size();
        assertEquals(3, sizeAfter);
        assertTrue(sizeBefore > sizeAfter);
    }
}
