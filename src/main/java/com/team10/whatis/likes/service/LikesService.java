package com.team10.whatis.likes.service;

import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.likes.dto.LikesResponseDto;
import com.team10.whatis.likes.entity.Likes;
import com.team10.whatis.likes.repository.LikesRepository;
import com.team10.whatis.likes.validator.LikesValidator;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final LikesValidator likesValidator;

    @Transactional
    public ResponseDto<?> likePost(Long id, Member member) {
        //프로젝트 확인
        Post post = likesValidator.validateIsExistPost(id);

        //좋아요 존재여부 확인
        Likes like = likesRepository.findByMemberAndPost(member, post);
        if(like == null){
            Likes newLikes = likesRepository.save(Likes.addLike(member, post));
            newLikes.setLikeStatus();
            post.updateLike(true);
            return ResponseDto.setSuccess(new LikesResponseDto(post,newLikes));
        }
        else {
            if(!like.isLikeStatus()){
                like.setLikeStatus();
                post.updateLike(true);
            }
            else{
                like.setLikeStatus();
                post.updateLike(false);
            }
            return ResponseDto.setSuccess(new LikesResponseDto(post,like));
        }
    }
}
