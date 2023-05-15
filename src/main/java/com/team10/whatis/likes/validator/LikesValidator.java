package com.team10.whatis.likes.validator;


import com.team10.whatis.post.entity.Post;
import com.team10.whatis.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class LikesValidator {

    private final PostRepository postRepository;

    //프로젝트 존재 여부 확인
    public Post validateIsExistPost(Long id){
        return postRepository.findById(id).orElseThrow( () -> new NoSuchElementException("존재하지 않는 프로젝트 입니다."));
    }
}
