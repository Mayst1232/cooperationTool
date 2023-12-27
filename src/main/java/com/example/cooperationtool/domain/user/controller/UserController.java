package com.example.cooperationtool.domain.user.controller;

import static com.example.cooperationtool.global.exception.ErrorCode.SIGNUP_FAIL;

import com.example.cooperationtool.domain.user.dto.request.SignupRequestDto;
import com.example.cooperationtool.domain.user.dto.response.ProfileResponseDto;
import com.example.cooperationtool.domain.user.dto.response.SignupResponseDto;
import com.example.cooperationtool.domain.user.service.UserService;
import com.example.cooperationtool.global.dto.response.RootResponseDto;
import com.example.cooperationtool.global.exception.ServiceException;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequestDto requestDto,
        BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            throw new ServiceException(SIGNUP_FAIL);
        }

        SignupResponseDto responseDto = userService.signUp(requestDto);

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("회원가입 성공")
            .data(responseDto)
            .build());
    }

    @GetMapping("/user/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ProfileResponseDto responseDto = userService.getProfile(userDetails.getUser());

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("프로필 조회 성공")
            .data(responseDto)
            .build());
    }

}
