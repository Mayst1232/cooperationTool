package com.example.cooperationtool.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.cooperationtool.domain.user.dto.request.ModifyProfileRequestDto;
import com.example.cooperationtool.domain.user.dto.request.SignupRequestDto;
import com.example.cooperationtool.domain.user.dto.response.ProfileResponseDto;
import com.example.cooperationtool.domain.user.dto.response.SignupResponseDto;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import com.example.cooperationtool.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("회원가입 서비스 API 테스트")
    class SignUp {

        @Test
        void success() {
            SignupRequestDto requestDto = SignupRequestDto.builder().username("username")
                .password("1234").nickname("nickname").build();

            given(passwordEncoder.encode(requestDto.getPassword())).willReturn(
                new BCryptPasswordEncoder().encode(requestDto.getPassword()));
            given(userRepository.findByUsername(any())).willReturn(Optional.empty());

            User user = User.builder().username(requestDto.getUsername())
                .password(requestDto.getPassword()).nickname(requestDto.getNickname())
                .introduce("자기소개를 입력해주세요").role(UserRoleEnum.USER).build();

            given(userRepository.save(any())).willReturn(user);

            SignupResponseDto responseDto = userService.signUp(requestDto);

            assertThat(responseDto.getUsername()).isEqualTo(requestDto.getUsername());
            assertThat(responseDto.getNickname()).isEqualTo(requestDto.getNickname());
            assertThat(responseDto.getRole()).isEqualTo(UserRoleEnum.USER);
        }
    }

    @Nested
    @DisplayName("유저 정보 조회 서비스 API 테스트")
    class GetProfile {

        @Test
        void success() {
            User user = User.builder()
                .username("hwang")
                .password("qwer")
                .nickname("mayst")
                .introduce("자기소개")
                .role(UserRoleEnum.USER)
                .build();

            given(userRepository.findById(any())).willReturn(Optional.of(user));

            ProfileResponseDto responseDto = userService.getProfile(user);

            assertThat(responseDto.getUsername()).isEqualTo("hwang");
            assertThat(responseDto.getNickname()).isEqualTo("mayst");
        }
    }

    @Nested
    @DisplayName("유저 정보 수정 서비스 API 테스트")
    class ModifyProfie {

        @Test
        void success() {
            User user = User.builder()
                .username("hwang")
                .password("qwer")
                .nickname("mayst")
                .introduce("자기소개")
                .role(UserRoleEnum.USER)
                .build();

            ModifyProfileRequestDto requestDto = ModifyProfileRequestDto.builder()
                .nickname("바꾼 닉네임")
                .introduce("바꾼 자기소개")
                .build();

            given(userRepository.findById(any())).willReturn(Optional.of(user));

            ProfileResponseDto responseDto = userService.modifyProfile(user, requestDto);

            assertThat(responseDto.getNickname()).isEqualTo(requestDto.getNickname());
            assertThat(responseDto.getIntroduce()).isEqualTo(requestDto.getIntroduce());
        }
    }

    @Nested
    @DisplayName("유저 정보 삭제 서비스 API 테스트")
    class DeleteUser {

        @Test
        void success() {
            User user = User.builder()
                .username("hwang")
                .password("qwer")
                .nickname("mayst")
                .introduce("자기소개")
                .role(UserRoleEnum.USER)
                .build();

            given(userRepository.findById(any())).willReturn(Optional.of(user));

            userService.deleteUser(user);

            verify(userRepository, times(1)).delete(any());
        }
    }

    @Nested
    @DisplayName("관리자 유저 정보 조회 서비스 API 테스트")
    class GetProfileAdmin {

        @Test
        void success() {
            User adminUser = User.builder()
                .username("hwang1234")
                .password("qwer1234")
                .nickname("mayst1234")
                .introduce("자기소개")
                .role(UserRoleEnum.ADMIN)
                .build();

            User user = User.builder()
                .username("hwang")
                .password("qwer")
                .nickname("mayst")
                .introduce("자기소개")
                .role(UserRoleEnum.USER)
                .build();

            given(userRepository.findByUsername(adminUser.getUsername())).willReturn(
                Optional.of(adminUser));
            given(userRepository.findById(any())).willReturn(Optional.of(user));

            ProfileResponseDto responseDto = userService.getProfileAdmin(adminUser, 2L);

            assertThat(responseDto.getUsername()).isEqualTo(user.getUsername());
            assertThat(responseDto.getNickname()).isEqualTo(user.getNickname());
            assertThat(responseDto.getIntroduce()).isEqualTo(user.getIntroduce());
        }
    }

    @Nested
    @DisplayName("관리자 특정 유저 정보 수정 서비스 API 테스트")
    class ModifyProfileAdmin {

        @Test
        void success() {
            User adminUser = User.builder()
                .username("hwang1234")
                .password("qwer1234")
                .nickname("mayst1234")
                .introduce("자기소개")
                .role(UserRoleEnum.ADMIN)
                .build();

            User user = User.builder()
                .username("hwang")
                .password("qwer")
                .nickname("mayst")
                .introduce("자기소개")
                .role(UserRoleEnum.USER)
                .build();

            ModifyProfileRequestDto requestDto = ModifyProfileRequestDto.builder()
                .nickname("change").introduce("change").build();

            given(userRepository.findByUsername(any())).willReturn(Optional.of(adminUser));

            given(userRepository.findById(any())).willReturn(Optional.of(user));

            ProfileResponseDto responseDto = userService.modifyProfileAdmin(adminUser, 2L,
                requestDto);

            assertThat(responseDto.getNickname()).isEqualTo("change");
            assertThat(responseDto.getIntroduce()).isEqualTo("change");
        }
    }

    @Nested
    @DisplayName("관리자 특정 유저 삭제 서비스 API 테스트")
    class DeleteUserAdmin {

        @Test
        void success() {
            User adminUser = User.builder()
                .username("hwang1234")
                .password("qwer1234")
                .nickname("mayst1234")
                .introduce("자기소개")
                .role(UserRoleEnum.ADMIN)
                .build();

            User user = User.builder()
                .username("hwang")
                .password("qwer")
                .nickname("mayst")
                .introduce("자기소개")
                .role(UserRoleEnum.USER)
                .build();

            Long userId = 2L;

            given(userRepository.findByUsername(adminUser.getUsername())).willReturn(
                Optional.of(adminUser));

            given(userRepository.findById(userId)).willReturn(Optional.of(user));

            userService.deleteUserAdmin(adminUser, userId);

            verify(userRepository, times(1)).delete(any());
        }
    }

}