package com.team10.whatis.member.dto;

import com.team10.whatis.post.dto.PostInfoRequestDto;
import com.team10.whatis.post.dto.PostPreviewResponseDto;
import com.team10.whatis.post.dto.PostResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class MyPageResponseDto {
    private List<PostPreviewResponseDto> myProject;
    private List<PostPreviewResponseDto> myFunding;

    public MyPageResponseDto(List<PostPreviewResponseDto> myProject, List<PostPreviewResponseDto> myFunding) {
        this.myProject = myProject;
        this.myFunding = myFunding;
    }
}
