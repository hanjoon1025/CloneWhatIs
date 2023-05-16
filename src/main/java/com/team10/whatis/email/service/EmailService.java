package com.team10.whatis.email.service;

import com.team10.whatis.email.dto.CodeRequestDto;
import com.team10.whatis.email.dto.EmailRequestDto;
import com.team10.whatis.email.entity.Email;
import com.team10.whatis.email.repository.EmailRepository;
import com.team10.whatis.email.validator.EmailValidator;
import com.team10.whatis.global.dto.ResponseDto;
import com.team10.whatis.global.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@PropertySource("classpath:application-secret.yml")
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;
    private final EmailValidator emailValidator;

    @Value("${spring.mail.username}")
    private String id;

    public MimeMessage createMessage(String to,String code)throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상 : "+ to);
        log.info("인증 번호 : " + code);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
        message.setSubject("WhatIs 회원가입 인증 코드: "); //메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += code;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id,"WhatIs 고객센터")); //보내는 사람의 메일 주소, 보내는 사람 이름

        return message;
    }

    // 인증코드 만들기
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((random.nextInt(10)));
        }
        return key.toString();
    }

    /*
        메일 발송
        sendSimpleMessage의 매개변수로 들어온 to는 인증번호를 받을 메일주소
        MimeMessage 객체 안에 내가 전송할 메일의 내용을 담아준다.
        bean으로 등록해둔 javaMailSender 객체를 사용하여 이메일 send
     */
    public ResponseDto<?> sendMessage(EmailRequestDto emailRequestDto){
        String code = createKey(); // 인증코드 생성
        Email email = Email.saveEmail(emailRequestDto); // 이메일 객체 생성
        MimeMessage message = null;
        try {
            message = createMessage(email.getEmail(),code); // 전송 메시지 삭성 메서드 호출
        } catch (MessagingException e) {
            ResponseDto.setBadRequest("메일 전송 실패",null);
        } catch (UnsupportedEncodingException e) {
            ResponseDto.setBadRequest("메일 전송 실패",null);
        }
        try{
            javaMailSender.send(message); // 메일 발송
        } catch (MailException es) {
            throw new CustomException("메일 전송 실패");
        }
        Email.updateCode(email,code);
        emailRepository.save(email);
        log.info("인증 코드 : "+code);
        return ResponseDto.setSuccess(null);
    }
    /*
        DB에 Email테이블에서 인증코드와 일치하는지 판별,
        일치여부와 상관없이 해당 레코드는 무조건 삭제
     */
    public ResponseDto<?> codeCheck(CodeRequestDto codeRequestDto){
        Email findEmail = emailValidator.validateExistEmail(codeRequestDto.getEmail());
        emailRepository.deleteById(codeRequestDto.getEmail());
        emailValidator.validateIsCorrectCode(findEmail, codeRequestDto.getCode());
        return ResponseDto.setSuccess(null);
    }
}