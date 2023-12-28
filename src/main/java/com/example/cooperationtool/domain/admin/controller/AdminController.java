package com.example.cooperationtool.domain.admin.controller;

import com.example.cooperationtool.domain.user.dto.response.ProfileResponseDto;
import com.example.cooperationtool.domain.user.service.UserService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users/profile/{userId}")
    public ResponseEntity<?> getProfileAdmin(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "userId") Long userId) {

        ProfileResponseDto responseDto = userService.getProfileAdmin(userDetails.getUser(), userId);

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("해당 유저의 정보는 다음과 같습니다.")
            .data(responseDto)
            .build()
        );
    }
}
