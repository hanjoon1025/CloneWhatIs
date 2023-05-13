package com.team10.whatis.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String email;
    private String password;
    private String username;
    private String passwordCheck;
    private int code;

    @Getter
    public static class auth {
        private String email;
    }
    @Getter
    public static class login {
        private String email;
        private String password;
    }



}
