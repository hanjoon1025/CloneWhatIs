package com.team10.whatis.email.service;

import com.team10.whatis.email.dto.CodeRequestDto;
import com.team10.whatis.email.dto.EmailRequestDto;
import com.team10.whatis.email.entity.Email;
import com.team10.whatis.email.repository.EmailRepository;
import com.team10.whatis.email.validator.EmailValidator;
import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.exception.CustomException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private EmailRepository emailRepository;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private EmailValidator emailValidator;
    @InjectMocks
    private EmailService emailService;

    @Test
    @DisplayName("인증메일 발송 성공 테스트")
    public void success_CreateMessage(){
        //given
        EmailRequestDto emailRequestDto = new EmailRequestDto("abc123@naver.com");
        MimeMessage mockMessage = mock(MimeMessage.class);
        //when
        when(javaMailSender.createMimeMessage()).thenReturn(mockMessage);
        ResponseDto<?> responseDto = emailService.sendMessage(emailRequestDto);
        //then
        assertThat(responseDto.getMessage()).isEqualTo("success");
    }

    @Test
    @DisplayName("인증메일 발송 실패 테스트")
    public void fail_CreateMessage(){
        //given
        EmailRequestDto emailRequestDto = new EmailRequestDto("abc123@naver.com");
        CustomException mockException = mock(CustomException.class);
        //when
        when(javaMailSender.createMimeMessage()).thenThrow(mockException);
        //then
        assertThatThrownBy(()->emailService.sendMessage(emailRequestDto)).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("인증코드 검증 성공 테스트")
    public void success_CodeCheck(){
        //given
        CodeRequestDto codeRequestDto = new CodeRequestDto("abc123@naver.com","123456");
        Email mockEmail = mock(Email.class);
        //when
        when(emailValidator.validateExistEmail(codeRequestDto.getEmail())).thenReturn(mockEmail);
        ResponseDto<?> responseDto = emailService.codeCheck(codeRequestDto);
        //then
        assertThat(responseDto.getMessage()).isEqualTo("success");
    }

    @Test
    @DisplayName("인증코드 검증 실패 테스트")
    public void fail_CodeCheck(){
        //given
        CodeRequestDto codeRequestDto = new CodeRequestDto("abc123@naver.com","123456");
        CustomException mockException = mock(CustomException.class);
        //when
        when(emailValidator.validateExistEmail(codeRequestDto.getEmail())).thenThrow(mockException);
        //then
        assertThatThrownBy(()->emailService.codeCheck(codeRequestDto)).isInstanceOf(CustomException.class);
    }
}