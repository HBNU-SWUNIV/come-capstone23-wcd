package com.wcd.userservice.repository;

import com.wcd.userservice.entity.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static com.wcd.userservice.enums.Gender.MALE;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("User 생성")
    void createMember() {
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

        // when
        Users result = userRepository.save(users);

        // then
        Assertions.assertThat(result.getName()).isEqualTo(users.getName());
    }

    @Test
    @DisplayName("LoginId로 User 불러오기")
    void findByLoginId() {
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
        userRepository.save(users);

        // when
        Users result = userRepository.findByLoginId("test1234");

        // then
        Assertions.assertThat(result.getName()).isEqualTo(users.getName());
    }
}