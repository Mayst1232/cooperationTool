package com.example.cooperationtool.domain.user.dto.response;

import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProfileResponseDto {

    String username;
    String nickname;
    String introduce;
    UserRoleEnum role;

    @Builder
    public ProfileResponseDto(String username, String nickname, String introduce,
        UserRoleEnum role) {
        this.username = username;
        this.nickname = nickname;
        this.introduce = introduce;
        this.role = role;
    }
}
