package com.team10.whatis.email.validator;

import com.team10.whatis.email.entity.Email;
import com.team10.whatis.email.repository.EmailRepository;
import com.team10.whatis.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailValidator {
    private final EmailRepository emailRepository;

    //이메일이 DB에 존재 여부 확인
    public Email validateExistEmail(String email) {
        return emailRepository.findById(email).orElseThrow(() -> new CustomException("인증을 요청한 이메일이 아닙니다."));
    }

    //인증코드 일치 여부 확인
    public void validateIsCorrectCode(Email email, String code) {
        if (!email.getCode().equals(code)) {
            throw new CustomException("인증 코드가 일치하지 않습니다.");
        }
    }

}
