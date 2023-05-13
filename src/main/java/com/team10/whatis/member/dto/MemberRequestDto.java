package com.team10.whatis.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String email;
    private String username;
    private String password;
    private String passwordCheck;

    @Getter
    public static class login {
        private String email;
        private String password;
    }

}
