package com.team10.whatis.post.controller;

import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.security.UserDetailsImpl;
import com.team10.whatis.post.dto.PostInfoRequestDto;
import com.team10.whatis.post.dto.PostRequestDto;
import com.team10.whatis.post.dto.PostStoryRequestDto;
import com.team10.whatis.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                                         @RequestPart(value="thumbnail", required = false) MultipartFile multipartFile,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePostInfo(id, postInfoRequestDto, multipartFile, userDetails.getMember());
    }

    @PutMapping("/{id}/story")
    public ResponseDto<?> updatePostStory(@PathVariable Long id,
                                          @Valid @RequestPart(value = "postStory") PostStoryRequestDto postStoryRequestDto,
                                          @RequestPart(value="projectImage", required = false) MultipartFile image,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePostStory(id, postStoryRequestDto, image, userDetails.getMember());
    }

    @DeleteMapping("/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(id, userDetails.getMember());
    }
}
