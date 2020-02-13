package com.example.memberservice.repository;

import com.example.memberservice.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Remove all inactive users from the database
     */
    @Transactional
    @Modifying
    @Query("delete from Member m where m.isActive = false")
    void removeAllInactive();

}
