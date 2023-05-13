package com.team10.whatis.global.jwt.repository;

import com.team10.whatis.global.jwt.entity.RefreshToken;
import com.team10.whatis.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}
