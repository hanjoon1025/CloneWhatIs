package com.team10.whatis.member.service;

import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.member.dto.MyPageResponseDto;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.post.dto.PostRequestDto;
import com.team10.whatis.post.dto.PostResponseDto;
import com.team10.whatis.post.repository.FundPostRepository;
import com.team10.whatis.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final PostRepository postRepository;

    public ResponseDto<MyPageResponseDto> findMyPage(Long memberId){
        List<PostResponseDto> myProjectList = postRepository.findAllByMemberId(memberId).stream().map(p -> new PostResponseDto(p)).collect(Collectors.toList());
        System.out.println(myProjectList.size());
        List<PostResponseDto> myFundingList = postRepository.findAllByMemberIdAndIsFunding(memberId).stream().map(p -> new PostResponseDto(p)).collect(Collectors.toList());
        return ResponseDto.setSuccess(new MyPageResponseDto(myProjectList,myFundingList));
    }
}
