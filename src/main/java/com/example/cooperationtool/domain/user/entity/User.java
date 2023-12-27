package com.example.cooperationtool.domain.user.entity;

import com.example.cooperationtool.domain.model.BaseEntity;
import com.example.cooperationtool.domain.user.dto.request.ModifyProfileRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String introduce;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    public User(String username, String password, String nickname, String introduce,
        UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.introduce = introduce;
        this.role = role;
    }

    public void update(ModifyProfileRequestDto requestDto) {
        if (requestDto.getNickname() != null) {
            this.nickname = requestDto.getNickname();
        }

        if (requestDto.getIntroduce() != null) {
            this.introduce = requestDto.getIntroduce();
        }
    }
}
