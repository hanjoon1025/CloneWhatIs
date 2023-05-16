package com.team10.whatis.post.controller;

import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.security.UserDetailsImpl;
import com.team10.whatis.post.dto.PostInfoRequestDto;
import com.team10.whatis.post.dto.PostRequestDto;
import com.team10.whatis.post.dto.PostResponseDto;
import com.team10.whatis.post.dto.PostStoryRequestDto;
import com.team10.whatis.post.entity.Category;
import com.team10.whatis.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseDto<Long> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postRequestDto, userDetails.getMember());
    }

    @PutMapping(value = "/{id}/info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<?> updatePostIndo(@PathVariable Long id,
                                         @Valid @RequestPart(value = "postInfo") PostInfoRequestDto postInfoRequestDto,
                                         @RequestPart(value = "thumbnail", required = false) MultipartFile multipartFile,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePostInfo(id, postInfoRequestDto, multipartFile, userDetails.getMember());
    }

    @PutMapping("/{id}/story")
    public ResponseDto<?> updatePostStory(@PathVariable Long id,
                                          @Valid @RequestPart(value = "postStory") PostStoryRequestDto postStoryRequestDto,
                                          @RequestPart(value = "projectImage", required = false) MultipartFile image,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePostStory(id, postStoryRequestDto, image, userDetails.getMember());
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getMember());
    }

    @GetMapping
    public ResponseDto<List<PostResponseDto>> postList(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                       @RequestParam(value = "search", required = false) String keyword,
                                                       @RequestParam(value = "category", required = false) Category category) {
        if (isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (keyword != null) {
                return postService.searchPost(pageable, keyword, userDetails.getMember());
            }
            return postService.findAllPosts(pageable, category, userDetails.getMember());
        }
        if (keyword != null) {
            return postService.searchPost(pageable, keyword, null);
        }
        return postService.findAllPosts(pageable, category, null);

    }

    @PostMapping("/{id}")
    public ResponseDto<?> fundingPost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.fundingPost(id, userDetails.getMember());
    }

    @GetMapping("/{id}")
    public ResponseDto<PostResponseDto> findPost(@PathVariable Long id) {
        if (isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return postService.findPost(id, userDetails.getMember());
        }
        return postService.findPost(id, null);
    }

    //로그인 여부 확인
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

}