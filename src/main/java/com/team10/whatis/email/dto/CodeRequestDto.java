package com.team10.whatis.email.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CodeRequestDto {
    private String email;
    private String code;

}
