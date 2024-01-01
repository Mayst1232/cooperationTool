package com.example.cooperationtool.domain.user.service;

import static com.example.cooperationtool.global.exception.ErrorCode.DUPLICATE_USERNAME;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_ADMIN;
import static com.example.cooperationtool.global.exception.ErrorCode.NOT_EXIST_USER;
import static com.example.cooperationtool.global.exception.ErrorCode.WRONG_ADMIN_CODE;

import com.example.cooperationtool.domain.user.dto.request.ModifyProfileRequestDto;
import com.example.cooperationtool.domain.user.dto.request.SignupRequestDto;
import com.example.cooperationtool.domain.user.dto.response.ProfileResponseDto;
import com.example.cooperationtool.domain.user.dto.response.SignupResponseDto;
import com.example.cooperationtool.domain.user.entity.User;
import com.example.cooperationtool.domain.user.entity.UserRoleEnum;
import com.example.cooperationtool.domain.user.repository.UserRepository;
import com.example.cooperationtool.global.exception.ServiceException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAlrnYxKZ0aHgTBcXukeZygoC";

    public SignupResponseDto signUp(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();

        String introduce = "자기소개를 입력해주세요";

        if (requestDto.getIntroduce() != null) {
            introduce = requestDto.getIntroduce();
        }

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new ServiceException(DUPLICATE_USERNAME);
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new ServiceException(WRONG_ADMIN_CODE);
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = User.builder()
            .username(username)
            .password(password)
            .nickname(nickname)
            .introduce(introduce)
            .role(role)
            .build();

        User savedUser = userRepository.save(user);

        return SignupResponseDto.builder()
            .username(savedUser.getUsername())
            .nickname(savedUser.getNickname())
            .role(savedUser.getRole())
            .build();
    }

    public ProfileResponseDto getProfile(User user) {
        User profileUser = findUser(user.getId());

        return ProfileResponseDto.builder()
            .user(profileUser)
            .build();
    }


    @Transactional
    public ProfileResponseDto modifyProfile(User user, ModifyProfileRequestDto requestDto) {
        User profileUser = findUser(user.getId());

        profileUser.update(requestDto);

        return ProfileResponseDto.builder()
            .user(profileUser)
            .build();
    }

    public void deleteUser(User user) {
        User findUser = findUser(user.getId());

        userRepository.delete(findUser);
    }

    public ProfileResponseDto getProfileAdmin(User user, Long userId) {

        User adminUser = findAdminUser(user.getUsername());
        User findUser = findUser(userId);

        return ProfileResponseDto.builder()
            .user(findUser)
            .build();
    }

    @Transactional
    public ProfileResponseDto modifyProfileAdmin(User user, Long userId,
        ModifyProfileRequestDto requestDto) {

        User adminUser = findAdminUser(user.getUsername());
        User modifyUser = findUser(userId);

        modifyUser.update(requestDto);

        return ProfileResponseDto.builder()
            .user(modifyUser)
            .build();
    }


    public void deleteUserAdmin(User user, Long userId) {
        User adminUser = findAdminUser(user.getUsername());
        User findUser = findUser(userId);

        userRepository.delete(findUser);
    }


    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new ServiceException(NOT_EXIST_USER)
        );
    }

    public User findAdminUser(String username) {
        User adminUser = userRepository.findByUsername(username).orElseThrow(
            () -> new ServiceException(NOT_EXIST_USER)
        );

        if (!adminUser.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new ServiceException(NOT_ADMIN);
        }

        return adminUser;
    }
}
