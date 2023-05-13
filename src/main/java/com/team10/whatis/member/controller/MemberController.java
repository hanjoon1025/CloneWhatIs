package com.team10.whatis.member.controller;


import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.member.dto.MemberRequestDto;
import com.team10.whatis.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto requestDto) {
        return memberService.signup(requestDto);

    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody MemberRequestDto.login requestDto, HttpServletResponse response) {
        return memberService.login(requestDto,response);
    }


}
