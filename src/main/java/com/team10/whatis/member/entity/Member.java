package com.team10.whatis.member.entity;


import com.team10.whatis.member.dto.MemberRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public static Member saveMember(MemberRequestDto requestDto, String password){
        return new Member(requestDto.getUsername(), password, requestDto.getEmail());

    }


}
