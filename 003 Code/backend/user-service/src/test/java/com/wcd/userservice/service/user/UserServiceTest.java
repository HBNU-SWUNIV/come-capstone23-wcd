package com.wcd.userservice.service.user;

import com.wcd.userservice.client.ClubServiceClient;
import com.wcd.userservice.dto.user.response.ResponseUserById;
import com.wcd.userservice.entity.Users;
import com.wcd.userservice.file.FileStore;
import com.wcd.userservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.wcd.userservice.enums.Gender.MALE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    // Test 주체
    UserService userService;

    // Test 협력자
    @MockBean
    UserRepository userRepository;

    @MockBean
    ClubServiceClient clubServiceClient;

    @MockBean
    FileStore fileStore;

    // Test를 실행하기 전마다 UserService에 가짜 객체를 주입시켜준다.
    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, clubServiceClient, fileStore);
    }

    @Test
    @DisplayName("user-id로 User 조회")
    void getUserById() {
        // given
        Users users = Users.builder()
                .loginId("test1234")
                .encryptedPwd("encryptedPwd")
                .name("kim")
                .phoneNumber("010-1234-1234")
                .birthday(LocalDate.of(2023,07,27))
                .gender(MALE)
                .profileImage("imageUrl")
                .build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(users));

        // when, then
        Assertions.assertThat(userService.getUserById(1L)).isInstanceOf(ResponseUserById.class);
    }
}