package com.team10.whatis.member.controller;


import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public ResponseDto<?> signup() {
        return ResponseDto.setSuccess("제발 되라");
    }

    @GetMapping("/login")
    public ResponseDto<?> login() {
        return ResponseDto.setSuccess("Testing LogIn");
    }

    @GetMapping("/auth")
    public ResponseDto<?> authEmail() {
       return ResponseDto.setSuccess("Testing Auth");
    }




}
