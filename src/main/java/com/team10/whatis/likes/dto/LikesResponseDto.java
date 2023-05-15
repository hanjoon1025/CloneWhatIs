package com.team10.whatis.likes.dto;

import com.team10.whatis.likes.entity.Likes;
import com.team10.whatis.post.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikesResponseDto {
    private int likeCount;
    private boolean likeStatus;

    public LikesResponseDto(Post post, Likes likes) {
        this.likeCount = post.getLikeCount();
        this.likeStatus = likes.isLikeStatus();
    }
}
