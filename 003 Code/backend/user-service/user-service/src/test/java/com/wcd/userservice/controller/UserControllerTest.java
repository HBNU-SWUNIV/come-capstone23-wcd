package com.wcd.userservice.controller;

import com.wcd.userservice.dto.user.response.ResponseUserById;
import com.wcd.userservice.entity.Users;
import com.wcd.userservice.service.user.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static com.wcd.userservice.enums.Gender.MALE;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserServiceImpl userService;

    @Test
    @WithMockUser(username = "testUser", roles = "USER") // 가짜 사용자로 인증 시도
    @DisplayName("회원 정보 조회")
    void getUserById() throws Exception {
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
        ReflectionTestUtils.setField(users,"id",1L);
        ResponseUserById response = ResponseUserById.builder().user(users).build();
        Mockito.when(userService.getUserById(1L)).thenReturn(response);

        // then
        mvc.perform(MockMvcRequestBuilders.get("/user/{user-id}", 1L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(response.getName()));
    }
}