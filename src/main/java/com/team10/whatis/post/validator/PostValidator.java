package com.team10.whatis.post.validator;

import com.team10.whatis.global.exception.CustomException;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.post.dto.PostStoryRequestDto;
import com.team10.whatis.post.entity.Category;
import com.team10.whatis.post.entity.Post;
import com.team10.whatis.post.entity.Tag;
import com.team10.whatis.post.repository.FundPostRepository;
import com.team10.whatis.post.repository.PostRepository;
import com.team10.whatis.post.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostValidator {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final FundPostRepository fundPostRepository;

    //게시글 존재 여부 확인
    public Post validateIsExistPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 프로젝트입니다.")
        );
    }

    //이미지 사이즈 확인
    public void validateImageSize(long size) {
        if (size > 5242880) throw new CustomException("5MB 이하의 이미지만 등록 가능합니다.");
    }

    //태그 존재 여부 확인
    public Tag validateIsExistTag(String tag) {
        Optional<Tag> findTag = tagRepository.findByName(tag);
        return findTag.orElse(null);
    }

    public void validatePostAuthor(Post post, Member member) {
        if (!post.getMember().getEmail().equals(member.getEmail())){
            throw new CustomException("권한이 없습니다.");
        }
    }

    public void validateIsFundingPost(Post post, Member member) {
        fundPostRepository.findByPostIdAndMemberId(post.getId(), member.getId()).ifPresent(fundPost -> {
            throw new CustomException("이미 펀딩한 프로젝트입니다.");
        });
    }

//    // 추가 검증 메서드들
//    public void validateProjectTitleLength(String title) {
//        if (title.length() > 40) {
//            throw new CustomException("프로젝트 제목은 40자까지 작성할 수 있습니다.");
//        }
//    }
//
//    public void validateFundingAmount(int amount) {
//        if (amount < 1000) {
//            throw new CustomException("펀딩 금액은 1천원 이상이어야 합니다.");
//        }
//    }
//
//    public void validateTargetAmount(int targetAmount) {
//        if (targetAmount < 500000 || targetAmount > 100000000) {
//            throw new CustomException("목표 금액은 50만원 이상 1억원 이하여야 합니다.");
//        }
//    }
//
//    public void validateDeadline(LocalDate deadline) {
//        if (deadline.isBefore(LocalDate.now())) {
//            throw new CustomException("마감일은 오늘보다 과거일 수 없습니다.");
//        }
//    }
//
//    public void validatePostStory(PostStoryRequestDto postStoryRequestDto) {
//        if (postStoryRequestDto.getSummary().length() > 100) {
//            throw new CustomException("프로젝트 요약은 100자까지 작성할 수 있습니다.");
//        }
//        if (postStoryRequestDto.getStoryBoard().length() > 1000) {
//            throw new CustomException("프로젝트 스토리는 1000자까지 작성할 수 있습니다.");
//        }
//    }
//
//    public void validateCategory(Category category) {
//        if (category == null) {
//            throw new CustomException("일치하는 카테고리가 없습니다.");
//        }
//    }


}
