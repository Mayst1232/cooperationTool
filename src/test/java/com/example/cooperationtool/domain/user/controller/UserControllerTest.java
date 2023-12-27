package com.example.cooperationtool.domain.user.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.cooperationtool.domain.user.dto.request.SignupRequestDto;
import com.example.cooperationtool.domain.user.dto.response.SignupResponseDto;
import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import com.example.cooperationtool.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Nested
    @DisplayName("유저 회원가입")
    class SignUp {

        @DisplayName("성공 케이스")
        @Test
        void success() throws Exception {
            SignupRequestDto requestDto = SignupRequestDto.builder()
                .username("username")
                .password("12341234")
                .nickname("nickname")
                .introduce("introduce")
                .build();

            String json = objectMapper.writeValueAsString(requestDto);

            SignupResponseDto responseDto = SignupResponseDto.builder()
                .username(requestDto.getUsername())
                .nickname(requestDto.getNickname())
                .role(UserRoleEnum.USER)
                .build();

            given(userService.signUp(requestDto)).willReturn(responseDto);

            mockMvc.perform(post("/api/user/signup")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(status().isOk(),
                    jsonPath("$.code").value("200"),
                    jsonPath("$.message").value("회원가입 성공"));
        }
    }
}