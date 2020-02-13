package com.example.memberservice.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private byte[] picture;
    private boolean isActive;
}
