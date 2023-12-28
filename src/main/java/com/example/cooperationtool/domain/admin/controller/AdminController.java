package com.example.cooperationtool.domain.admin.controller;

import static com.example.cooperationtool.global.exception.ErrorCode.MODIFY_PROFILE_FAILED;

import com.example.cooperationtool.domain.user.dto.request.ModifyProfileRequestDto;
import com.example.cooperationtool.domain.user.dto.response.ProfileResponseDto;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PatchMapping("/users/profile/{userId}")
    public ResponseEntity<?> modifyProfileAdmin(
        @Valid @RequestBody ModifyProfileRequestDto requestDto,
        BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "userId") Long userId) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            throw new ServiceException(MODIFY_PROFILE_FAILED);
        }

        ProfileResponseDto responseDto = userService.modifyProfileAdmin(userDetails.getUser(),
            userId, requestDto);

        return ResponseEntity.ok(RootResponseDto.builder()
            .code("200")
            .message("해당 유저의 정보를 수정하였습니다.")
            .data(responseDto)
            .build());
    }
}
