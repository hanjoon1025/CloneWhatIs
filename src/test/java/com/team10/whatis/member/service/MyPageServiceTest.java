package com.team10.whatis.member.service;

import com.team10.whatis.likes.repository.LikesRepository;
import com.team10.whatis.member.dto.MemberRequestDto;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.post.dto.PostRequestDto;
import com.team10.whatis.post.entity.Category;
import com.team10.whatis.post.entity.Post;
import com.team10.whatis.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyPageServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private LikesRepository likesRepository;
    @InjectMocks
    private MyPageService myPageService;

    @Test
    @DisplayName("내가만든 프로젝트 리스트 가져오기 성공 테스트")
    public void success_findMyProjectList(){
        List<Post> myProjectList = new ArrayList<>();
        Member member = Member.saveMember(new MemberRequestDto(), "비밀번호");
        for(int i=0;i<10;i++){
            myProjectList.add(new Post(new PostRequestDto(Category.Beauty),member));
        }
        when(postRepository.findAllByMemberId(1L)).thenReturn(myProjectList);
        assertThat(myPageService.findMyPage(1L).getData().getMyProject().size()).isEqualTo(myProjectList.size());
    }

    @Test
    @DisplayName("내가 펀딩한 프로젝트 리스트 가져오기 성공 테스트")
    public void success_findMyFundingList(){
        List<Post> myFudingList = new ArrayList<>();
        Member member = Member.saveMember(new MemberRequestDto(), "비밀번호");
        for(int i=0;i<10;i++){
            myFudingList.add(new Post(new PostRequestDto(Category.Beauty),member));
        }
        when(postRepository.findAllByMemberIdAndIsFunding(1L)).thenReturn(myFudingList);
        assertThat(myPageService.findMyPage(1L).getData().getMyFunding().size()).isEqualTo(myFudingList.size());
    }
}