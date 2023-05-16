package com.team10.whatis.likes.entity;

import com.team10.whatis.member.entity.Member;
import com.team10.whatis.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private boolean likeStatus;


    private Likes(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public static Likes addLike(Member member, Post post) {
        return new Likes(member, post);
    }

    public void setLikeStatus() {
        this.likeStatus = !(this.likeStatus);
    }
}
