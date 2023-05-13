package com.team10.whatis.post.validator;

import com.team10.whatis.post.entity.Post;
import com.team10.whatis.post.entity.Tag;
import com.team10.whatis.post.repository.PostRepository;
import com.team10.whatis.post.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostValidator {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    //게시글 존재 여부 확인
    public Post validateIsExistPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 프로젝트입니다.")
        );
    }

    //이미지 사이즈 확인
    public void validateImageSize(long size) {
        if (size > 5242880) throw new IllegalArgumentException("5MB 이하의 이미지만 등록 가능합니다.");
    }

    //태그 존재 여부 확인
    public Tag validateIsExistTag(String tag) {
        Optional<Tag> findTag = tagRepository.findByTagName(tag);
        return findTag.orElse(null);
    }
}
