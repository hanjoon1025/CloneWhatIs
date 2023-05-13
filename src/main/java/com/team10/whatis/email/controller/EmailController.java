package com.team10.whatis.email.controller;

import com.team10.whatis.email.dto.CodeRequestDto;
import com.team10.whatis.email.dto.EmailRequestDto;
import com.team10.whatis.email.entity.Email;
import com.team10.whatis.email.service.EmailService;
import com.team10.whatis.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/email")
@RestController
public class EmailController {

    private final EmailService emailService;

    /*
    가입시 메일을 작성하고 , 인증을 위해 해당 메일에
    인증코드를 보내는 컨트롤러
     */
    @PostMapping("/auth")
    public ResponseDto<?> mailSend(@RequestBody EmailRequestDto emailRequestDto){
        return emailService.sendMessage(emailRequestDto);
    }
    /*
    가입시 작성한 메일에 날아온 인증코드를
    검증하는 컨트롤러
     */
    @PostMapping("/check")
    public ResponseDto<?> codeCheck(@RequestBody CodeRequestDto codeRequestDto){
        return emailService.codeCheck(codeRequestDto);
    }
}