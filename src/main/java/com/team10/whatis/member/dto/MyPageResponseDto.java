package com.team10.whatis.member.dto;

import com.team10.whatis.post.dto.PostInfoRequestDto;
import com.team10.whatis.post.dto.PostResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class MyPageResponseDto {
    private List<PostResponseDto> myProject;
    private List<PostResponseDto> myFunding;

    public MyPageResponseDto(List<PostResponseDto> myProject, List<PostResponseDto> myFunding) {
        this.myProject = myProject;
        this.myFunding = myFunding;
    }
}
