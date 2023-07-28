package com.wcd.userservice.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.wcd.userservice.enums.Gender.MALE;

class UsersTest {

    @Test
    @DisplayName("유저가 생성되는지 확인하는 테스트")
    void createUser() {
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

        // when, then
        Assertions.assertThat(users.getName()).isEqualTo("kim");
        Assertions.assertThat(users.getPhoneNumber()).isEqualTo("010-1234-1234");
    }
}