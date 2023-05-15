package com.team10.whatis.likes.controller;


import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.security.UserDetailsImpl;
import com.team10.whatis.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/{id}")
    public ResponseDto<?> likePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likesService.likePost(id, userDetails.getMember());
    }
}
