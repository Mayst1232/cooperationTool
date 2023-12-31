package com.example.cooperationtool.domain.admin.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.cooperationtool.domain.user.dto.request.ModifyProfileRequestDto;
import com.example.cooperationtool.domain.user.dto.response.ProfileResponseDto;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import com.example.cooperationtool.domain.user.service.UserService;
import com.example.cooperationtool.domain.user.support.MockSpringSecurityFilter;
import com.example.cooperationtool.global.config.WebSecurityConfig;
import com.example.cooperationtool.global.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = {AdminController.class}, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)})
@ActiveProfiles("test")
class AdminControllerTest {

    private MockMvc mockMvc;

    private Principal mockPrincipal;

    private User testUser;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    UserService userService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter())).alwaysDo(print()).build();
    }

    private void mockUserSetup() {
        String username = "admin";
        String password = "admin";
        String nickname = "Mayst";
        String introduce = "저는 관리자입니다.";
        testUser = User.builder().username(username).password(password).nickname(nickname)
            .introduce(introduce).role(UserRoleEnum.ADMIN).build();
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "",
            testUserDetails.getAuthorities());
    }

    @Nested
    @DisplayName("관리자 특정 유저 정보 조회")
    class GetProfileAdmin {

        @DisplayName("성공 케이스")
        @Test
        void success() throws Exception {
            mockUserSetup();

            User findUser = User.builder()
                .username("findUser")
                .nickname("nickname")
                .introduce("자기소개")
                .role(UserRoleEnum.USER)
                .build();

            ProfileResponseDto responseDto = ProfileResponseDto.builder()
                .user(findUser).build();

            given(userService.getProfileAdmin(any(), any())).willReturn(responseDto);

            mockMvc.perform(get("/api/admin/users/profile/2")
                    .principal(mockPrincipal))
                .andExpectAll(status().isOk(),
                    jsonPath("$.code").value("200"),
                    jsonPath("$.message").value("해당 유저의 정보는 다음과 같습니다."),
                    jsonPath("$.data.username").value("findUser"));
        }

    }

    @Nested
    @DisplayName("관리자 특정 유저 정보 수정")
    class ModifyProfileAdmin {

        @Test
        void success() throws Exception {
            mockUserSetup();

            Long userId = 2L;

            ModifyProfileRequestDto requestDto = ModifyProfileRequestDto.builder()
                .nickname("change").introduce("change Introduce").build();

            User findUser = User.builder()
                .username("findUser")
                .nickname(requestDto.getNickname())
                .introduce(requestDto.getIntroduce())
                .role(UserRoleEnum.USER)
                .build();

            String json = objectMapper.writeValueAsString(requestDto);

            ProfileResponseDto responseDto = ProfileResponseDto.builder()
                .user(findUser).build();

            given(userService.modifyProfileAdmin(testUser, userId, requestDto)).willReturn(
                responseDto);

            mockMvc.perform(patch("/api/admin/users/profile/{userId}", userId)
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(mockPrincipal))
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.code").value("200"),
                    jsonPath("$.message").value("해당 유저의 정보를 수정하였습니다.")
                );
        }
    }

    @Nested
    @DisplayName("관리자 특정 유저 삭제")
    class DeleteUserAdmin {

        @Test
        void success() throws Exception {
            mockUserSetup();

            Long userId = 2L;

            mockMvc.perform(delete("/api/admin/users/{userId}", userId)
                    .principal(mockPrincipal))
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.code").value("200"),
                    jsonPath("$.message").value("해당 유저를 탈퇴시켰습니다.")
                );
        }
    }

}