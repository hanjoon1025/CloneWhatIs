package com.team10.whatis.likes.repository;

import com.team10.whatis.likes.entity.Likes;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByMemberAndPost(Member member, Post post);

    List<Likes> findAllByPost(Post post);
}
