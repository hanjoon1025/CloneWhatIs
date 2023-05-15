package com.team10.whatis.member.controller;

import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.security.UserDetailsImpl;
import com.team10.whatis.member.dto.MyPageResponseDto;
import com.team10.whatis.member.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    @GetMapping("/mypage") // 마이페이지 컨트롤러
    public ResponseDto<MyPageResponseDto> findMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return myPageService.findMyPage(userDetails.getMember().getId());
    }
}
