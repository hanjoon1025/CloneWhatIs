package com.team10.whatis.post.repository;

import com.team10.whatis.post.entity.FundPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FundPostRepository extends JpaRepository<FundPost, Long> {
    Optional<FundPost> findByPostIdAndMemberId(Long postId, Long memberId);
}
