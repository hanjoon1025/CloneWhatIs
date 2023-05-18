package com.team10.whatis.member.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {

    private String email;
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@!%*?&()_])[A-Za-z\\d$@!%*?&()_]{8,15}$", message = "비밀번호는 최소 8자 이상 15자 이하이며 알파벳 대소문자, 숫자와 특수문자로 구성되어야 합니다.")
    private String password;
    private String passwordCheck;

    @Getter
    public static class login {
        private String email;
        private String password;
    }
}
