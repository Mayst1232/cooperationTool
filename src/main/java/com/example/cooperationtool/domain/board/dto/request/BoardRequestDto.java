package com.example.cooperationtool.domain.board.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{2,10}$", message = "영문자와 숫자 중 2글자 이상 10글자 이하의 조합입니다.")
    private String title;

    @Pattern(regexp = "^[a-zA-Z0-9]{2,15}$", message = "영문자와 숫자 중 2글자 이상 15글자 이하의 조합입니다.")
    private String content;


}
