package com.team10.whatis.member.service;

import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.jwt.JwtUtil;
import com.team10.whatis.global.jwt.dto.TokenDto;
import com.team10.whatis.global.jwt.entity.RefreshToken;
import com.team10.whatis.global.jwt.repository.RefreshTokenRepository;
import com.team10.whatis.member.dto.MemberRequestDto;
import com.team10.whatis.member.entity.Member;
import com.team10.whatis.member.repository.MemberRepository;
import com.team10.whatis.member.validator.MemberValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberValidator memberValidator;

    public ResponseDto<?> signup(MemberRequestDto requestDto) {
        //비밀번호 검증 홧인
        memberValidator.validatePasswordCheck(requestDto);

        // 비밀번호 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        memberValidator.validateIsExistMember(requestDto.getEmail());

        // 사용자 DB에 저장
        memberRepository.saveAndFlush(Member.saveMember(requestDto, password));

        return ResponseDto.setSuccess(null);
    }

    public ResponseDto<?> login(MemberRequestDto.login requestDto, HttpServletResponse response) {
        // 이메일, 비밀번호 확인
        Member member = memberValidator.validateEmailAndPassword(requestDto);

        //Token 생성
        TokenDto tokenDto = jwtUtil.createAllToken(member.getEmail());

        //RefreshToken 있는지 확인
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMember(member);

        //있으면 새 토큰 발급 후 업데이트
        //없으면 새로 만들고 DB에 저장
        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            refreshTokenRepository.saveAndFlush(RefreshToken.saveToken(tokenDto.getRefreshToken(), member));
        }

        //header에 accesstoken, refreshtoken 추가
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
        return ResponseDto.setSuccess(null);
    }

}
