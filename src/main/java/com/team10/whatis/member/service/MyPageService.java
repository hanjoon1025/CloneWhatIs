package com.team10.whatis.member.service;

import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.security.UserDetailsImpl;
import com.team10.whatis.likes.entity.Likes;
import com.team10.whatis.likes.repository.LikesRepository;
import com.team10.whatis.member.dto.MemberRequestDto;
import com.team10.whatis.member.dto.MyPageResponseDto;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.member.repository.MemberRepository;
import com.team10.whatis.post.dto.PostPreviewResponseDto;
import com.team10.whatis.post.dto.PostRequestDto;
import com.team10.whatis.post.dto.PostResponseDto;
import com.team10.whatis.post.entity.Post;
import com.team10.whatis.post.repository.FundPostRepository;
import com.team10.whatis.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;

    /*
        내가 생성한 프로젝트와 , 펀딩한 프로젝트들을 가지고 온 뒤,
        MyPageResponseDto에 담아서 반환해줌.
     */
    public ResponseDto<MyPageResponseDto> findMyPage(Long memberId){
        List<PostPreviewResponseDto> myProjectList = getAllPostsByUserDetails(memberId, postRepository.findAllByMemberId(memberId));
        List<PostPreviewResponseDto> myFundingList = getAllPostsByUserDetails(memberId,postRepository.findAllByMemberIdAndIsFunding(memberId));
        return ResponseDto.setSuccess(new MyPageResponseDto(myProjectList,myFundingList));
    }

    private List<PostPreviewResponseDto> getAllPostsByUserDetails(Long  memberId, List<Post> allPosts){
        List<PostPreviewResponseDto> postList = new ArrayList<>();
        for(Post post : allPosts){
            PostPreviewResponseDto postPreviewResponseDto = getPostByUserDetails(memberId, post);
            postList.add(postPreviewResponseDto);
        }
        return postList;
    }

    private PostPreviewResponseDto getPostByUserDetails(Long memberId, Post post){
        PostPreviewResponseDto postPreviewResponseDto = new PostPreviewResponseDto(post, false);
        for(Likes likes : likesRepository.findAllByPost(post)){
            if(likes.getMember().getId().equals(memberId)){
                postPreviewResponseDto.setLikeStatus(true);
                break;
            }
        }
        return postPreviewResponseDto;
    }
}
