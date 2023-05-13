package com.team10.whatis.email.controller;

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

    @PostMapping("/auth")
    @ResponseBody
    public ResponseDto mailSend(@RequestBody EmailRequestDto emailRequestDto){
        return emailService.sendMessage(emailRequestDto);
    }
}