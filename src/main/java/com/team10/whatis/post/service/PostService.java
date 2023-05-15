package com.team10.whatis.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.post.dto.PostInfoRequestDto;
import com.team10.whatis.post.dto.PostRequestDto;
import com.team10.whatis.post.dto.PostResponseDto;
import com.team10.whatis.post.dto.PostStoryRequestDto;
import com.team10.whatis.post.entity.FundPost;
import com.team10.whatis.post.entity.Post;
import com.team10.whatis.post.entity.Tag;
import com.team10.whatis.post.repository.FundPostRepository;
import com.team10.whatis.post.repository.PostRepository;
import com.team10.whatis.post.repository.TagRepository;
import com.team10.whatis.post.validator.PostValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostValidator postValidator;
    private final TagRepository tagRepository;
    private final FundPostRepository fundPostRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    /**
     * 프로젝트 생성
     */
    public ResponseDto<Long> createPost(PostRequestDto postRequestDto, Member member) {
        Post post = new Post(postRequestDto, member);
        postRepository.save(post);
        return ResponseDto.setSuccess(post.getId());
    }

    /**
     * 프로젝트 정보 업데이트
     */
    public ResponseDto updatePostInfo(Long id, PostInfoRequestDto postInfoRequestDto, MultipartFile multipartFile, Member member) {
        Post post = postValidator.validateIsExistPost(id);
        postValidator.validatePostAuthor(post, member);

        //썸네일 변경
        if (multipartFile != null) {
            String thumbnailUrl = uploadImage(multipartFile);
            post.setThumbnail(thumbnailUrl);
        }

        //프로젝트 정보 변경
        post.updatePostInfo(postInfoRequestDto);

        //태그 중복 제거
        List<String> distinctTags = postInfoRequestDto.getSearchTag().stream().distinct().collect(Collectors.toList());
        makeTag(distinctTags, post);

        return ResponseDto.setSuccess(null);
    }

    /**
     * 프로젝트 태그 생성
     */
    public void makeTag(List<String> tags, Post post) {
        post.getTags().clear();
        for (String tagName : tags) {
            Tag tag = postValidator.validateIsExistTag(tagName);

            //태그가 존재하지 않으면 Tag 생성
            if (tag == null) {
                tag = new Tag(tagName);
                tagRepository.save(tag);
            }

            //프로젝트에 tag 추가
            post.saveTags(tag);
        }
    }

    /**
     * 프로젝트 스토리 업데이트
     */
    public ResponseDto<?> updatePostStory(Long id, PostStoryRequestDto postStoryRequestDto, MultipartFile multipartFile, Member member) {
        Post post = postValidator.validateIsExistPost(id);
        postValidator.validatePostAuthor(post, member);

        //프로젝트 이미지 변경
        if (multipartFile != null) {
            String projectImageUrl = uploadImage(multipartFile);
            post.setProjectImage(projectImageUrl);
        }

        //프로젝트 스토리 변경
        post.updatePostStory(postStoryRequestDto);
        return ResponseDto.setSuccess(null);
    }

    /**
     * 프로젝트 삭제
     */
    public ResponseDto<?> deletePost(Long id, Member member) {
        Post post = postValidator.validateIsExistPost(id);
        postValidator.validatePostAuthor(post, member);

        postRepository.delete(post);
        return ResponseDto.setSuccess(null);
    }

    /**
     * s3 이미지 업로드
     */
    public String uploadImage(MultipartFile image) {
        //중복된 이름 방지를 위한 UUID 붙이기
        String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();

        //메타 데이터 설정
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(image.getContentType());

        //이미지 사이즈 확인
        postValidator.validateImageSize(image.getSize());
        objMeta.setContentLength(image.getSize());

        try {
            objMeta.setContentLength(image.getInputStream().available());
            //s3에 파일 업로드
            amazonS3.putObject(bucket, fileName, image.getInputStream(), objMeta);
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 업로드에 실패했습니다.");
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    /**
     * 제목과 태그에 keyword가 포함되는 프로젝트 검색
     */
    public ResponseDto<List<PostResponseDto>> searchPost(Pageable pageable, String keyword) {
        Page<Post> allPosts = postRepository.findByTitleContainingOrTagsTagNameContaining(pageable, keyword, keyword);
        List<PostResponseDto> postList = allPosts.getContent().stream().map(PostResponseDto::new).collect(Collectors.toList());

        //TODO 프로젝트마다 좋아요 여부 확인
        return ResponseDto.setSuccess(postList);
    }

    /**
     * 프로젝트 전체 조회
     */
    public ResponseDto<List<PostResponseDto>> findAllPosts(Pageable pageable) {
        Page<Post> allPosts = postRepository.findAll(pageable);
        List<PostResponseDto> postList = allPosts.getContent().stream().map(PostResponseDto::new).collect(Collectors.toList());

        //TODO 프로젝트마다 좋아요 여부 확인
        return ResponseDto.setSuccess(postList);
    }

    /**
     * 프로젝트 상세 조회
     */
    public ResponseDto<PostResponseDto> findPost(Long id) {
        Post post = postValidator.validateIsExistPost(id);
        return ResponseDto.setSuccess(new PostResponseDto(post));
    }

    /**
     * 프로젝트 펀딩
     */
    public ResponseDto<?> fundingPost(Long id, Member member) {
        Post post = postValidator.validateIsExistPost(id);
        postValidator.validateIsFundingPost(post, member);

        FundPost fundPost = new FundPost(post, member);
        fundPostRepository.save(fundPost);
        return ResponseDto.setSuccess(null);
    }
}
