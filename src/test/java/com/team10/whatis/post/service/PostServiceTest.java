package com.team10.whatis.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.entity.StatusCode;
import com.team10.whatis.member.dto.MemberRequestDto;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.member.repository.MemberRepository;
import com.team10.whatis.post.dto.PostInfoRequestDto;
import com.team10.whatis.post.dto.PostRequestDto;
import com.team10.whatis.post.dto.PostResponseDto;
import com.team10.whatis.post.dto.PostStoryRequestDto;
import com.team10.whatis.post.entity.Category;
import com.team10.whatis.post.entity.Post;
import com.team10.whatis.post.repository.FundPostRepository;
import com.team10.whatis.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class PostServiceTest {
    @Mock
    private AmazonS3 amazonS3;
    private Member member;
    private PostRepository postRepository;
    private MemberRepository memberRepository;
    private FundPostRepository fundPostRepository;

    @InjectMocks
    private PostService postService;


    public PostServiceTest(@Autowired PostRepository postRepository,
                           @Autowired PostService postService,
                           @Autowired MemberRepository memberRepository,
                           @Autowired FundPostRepository fundPostRepository
    ) {
        this.postRepository = postRepository;
        this.postService = postService;
        this.memberRepository = memberRepository;
        this.fundPostRepository = fundPostRepository;
    }

    @BeforeEach
    @DisplayName("기본 세팅")
    public void setUp(){
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setEmail("abc123@naver.com");
        memberRequestDto.setPassword("Test12345!");
        memberRequestDto.setUsername("테스트");
        memberRequestDto.setPasswordCheck("Test12345!");
        member = Member.saveMember(memberRequestDto,"Test12345!");
        memberRepository.save(member);
        PostRequestDto postRequestDto = new PostRequestDto(Category.Beauty);
        postService.createPost(postRequestDto, member);
    }

    @Test
    @DisplayName("프로젝트 생성 성공 테스트")
    public void success_createPost(){
        PostRequestDto postRequestDto = new PostRequestDto(Category.Beauty);
        ResponseDto<Long> response = postService.createPost(postRequestDto, member);
        assertThat(response.getStatus()).isEqualTo(StatusCode.OK);
    }

    @Test
    @DisplayName("프로젝트 업데이트 성공 테스트")
    public void success_updatePostInfo() throws IOException {
        //given
        PostInfoRequestDto postInfoRequestDto = new PostInfoRequestDto();
        postInfoRequestDto.setTitle("스즈메의 문단속");
        postInfoRequestDto.setTargetAmount(5000000);
        postInfoRequestDto.setPrice(10000);
        postInfoRequestDto.setDeadLine(LocalDate.now());
        postInfoRequestDto.setSearchTag(new ArrayList<>());
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("test.png");
        when(multipartFile.getContentType()).thenReturn("image/png");
        when(multipartFile.getSize()).thenReturn(1024L);
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream("test data".getBytes()));
        Post post = postRepository.findAllByMemberId(member.getId()).get(0);
        postService.updatePostInfo(post.getId(), postInfoRequestDto, multipartFile, member);
        assertThat(postRepository.findById(post.getId()).get().getTitle()).isEqualTo("스즈메의 문단속");
    }

    @Test
    @DisplayName("프로젝트 삭제 성공 테스트")
    public void success_deletePost(){
        Post post = postRepository.findAllByMemberId(member.getId()).get(0);
        ResponseDto<?> responseDto = postService.deletePost(post.getId(), member);
        assertThat(responseDto.getStatus()).isEqualTo(StatusCode.OK);
    }

    @Test
    @DisplayName("프로젝트 아이디로 가져오기 성공 테스트")
    public void success_findPost(){
        Post post = postRepository.findAllByMemberId(member.getId()).get(0);
        ResponseDto<PostResponseDto> responseDto = postService.findPost(post.getId(), member);
        assertThat(responseDto.getData().getId()).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("프로젝트 펀딩하기 성공 테스트")
    public void success_fundingPost(){
        Post post = postRepository.findAllByMemberId(member.getId()).get(0);
        postService.fundingPost(post.getId(),member);
        assertThat(fundPostRepository.findByPostIdAndMemberId(post.getId(),member.getId()).isPresent())
                .isTrue();
    }

    @Test
    @DisplayName("프로젝트 스토리 업데이트 성공 테스트")
    public void success_updatePostStory() throws IOException {
        PostStoryRequestDto postStoryRequestDto = new PostStoryRequestDto();
        postStoryRequestDto.setStoryBoard("test");
        postStoryRequestDto.setSummary("test");
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("test.png");
        when(multipartFile.getContentType()).thenReturn("image/png");
        when(multipartFile.getSize()).thenReturn(1024L);
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream("test data".getBytes()));
        Post post = postRepository.findAllByMemberId(member.getId()).get(0);
        postService.updatePostStory(post.getId(), postStoryRequestDto, multipartFile, member);
        assertThat(postRepository.findById(post.getId()).get().getSummary()).isEqualTo("test");
    }
}