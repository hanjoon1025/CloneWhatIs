package com.team10.whatis.likes.validator;


import com.team10.whatis.global.exception.CustomException;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.post.entity.Post;
import com.team10.whatis.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikesValidator {

    private final PostRepository postRepository;

    //프로젝트 존재 여부 확인
    public Post validateIsExistPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new CustomException("존재하지 않는 프로젝트 입니다."));
    }

    // 로그인 여부 확인
    public void validateIsLogin(Member member) {
        if (member == null) {
            throw new CustomException("로그인한 회원이 아닙니다.");
        }
    }
}
