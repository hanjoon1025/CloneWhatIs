package com.team10.whatis.member.validator;

import com.team10.whatis.member.dto.MemberRequestDto;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //비밀번호 확인 일치 여부
    public void validatePasswordCheck(MemberRequestDto requestDto){
        if(!requestDto.getPassword().equals(requestDto.getPasswordCheck())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    //회원 존재 여부
    public void validateIsExistMember(String email){
        Optional<Member> found = memberRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 회원입니다.");
        }
    }

    //이메일, 비밀번호 일치여부
    public Member validateEmailAndPassword(MemberRequestDto.login requestDto){
        // 이메일 확인
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () ->  new NoSuchElementException("이메일 또는 비밀번호가 일치하지 않습니다.")
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())){
           throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");

        }
        return member;
    }


}
