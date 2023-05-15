package com.team10.whatis.member.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.jwt.JwtUtil;
import com.team10.whatis.member.dto.MemberRequestDto;
import com.team10.whatis.member.service.KakaoService;
import com.team10.whatis.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final KakaoService kakaoService;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto requestDto) {
        return memberService.signup(requestDto);

    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody MemberRequestDto.login requestDto, HttpServletResponse response) {
        return memberService.login(requestDto,response);
    }


    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        ResponseDto<?> result = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        // 이제 response의 header에서 token을 가져옵니다.
        String createToken = response.getHeader(JwtUtil.ACCESS_TOKEN);
        Cookie cookie = new Cookie(JwtUtil.ACCESS_TOKEN, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/posts";
    }

}
