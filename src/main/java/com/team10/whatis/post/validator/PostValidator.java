package com.team10.whatis.post.validator;

import com.team10.whatis.member.entity.Member;
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
        if (size > 5242880) throw new IllegalArgumentException("5MB 이하의 이미지만 등록 가능합니다.");
    }

    //태그 존재 여부 확인
    public Tag validateIsExistTag(String tag) {
        Optional<Tag> findTag = tagRepository.findByName(tag);
        return findTag.orElse(null);
    }

    public void validatePostAuthor(Post post, Member member) {
        if (!post.getMember().getEmail().equals(member.getEmail())){
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    public void validateIsFundingPost(Post post, Member member) {
        fundPostRepository.findByPostIdAndMemberId(post.getId(), member.getId()).ifPresent(fundPost -> {
            throw new IllegalArgumentException("이미 펀딩한 프로젝트입니다.");
        });
    }
}
