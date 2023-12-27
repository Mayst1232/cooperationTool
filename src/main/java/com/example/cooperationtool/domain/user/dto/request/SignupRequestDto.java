package com.example.cooperationtool.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @NotBlank
    @Size(max = 15)
    private String username;

    @NotBlank
    @Size(max = 60)
    private String password;

    @NotBlank
    @Size(max = 10)
    private String nickname;

    private String introduce;

    @Builder.Default
    private String adminToken = "";

    @Builder.Default
    boolean admin = false;
}
