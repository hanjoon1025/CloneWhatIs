package com.team10.whatis.email.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeRequestDto {
    private String email;
    private String code;

}
