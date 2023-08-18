<template>
  <v-container class="SignupPage">
    <v-row justify="center">
      <v-col cols="12" md="6">
        <v-card>
          <v-card-title>회원가입</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="SignupSubmit">
              <v-text-field
                  id="name"
                  v-model="name"
                  placeholder="이름"
              ></v-text-field>

              <v-text-field
                  id="loginId"
                  v-model="loginId"
                  placeholder="아이디"
              ></v-text-field>

              <v-text-field
                  id="password"
                  type="password"
                  v-model="password"
                  placeholder="비밀번호"
              ></v-text-field>

              <v-text-field
                  id="pwdCheck"
                  type="password"
                  v-model="pwCheck"
                  placeholder="비밀번호 확인"
              ></v-text-field>

              <v-text-field
                  id="phoneNumber"
                  v-model="phoneNumber"
                  placeholder="전화번호"
                  type="tel"
              ></v-text-field>

              <v-text-field
                  id="birthday"
                  v-model="birthday"
                  placeholder="생년월일"
                  type="date"
              ></v-text-field>

              <v-select
                  id="gender"
                  v-model="gender"
                  :items="['MALE', 'FEMALE']"
                  placeholder="성별"
              ></v-select>

              <v-btn class="mt-3" block type="submit">
                회원가입
              </v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {ref} from "vue";
import axios from "axios";

export default {
  setup() {
    const name = ref("");
    const loginId = ref("");
    const password = ref("");
    const pwCheck = ref("");
    const phoneNumber = ref("");
    const birthday = ref("");
    const gender = ref("");

    const SignupSubmit = async () => {
      // JSON 형식의 데이터
      let SignupData = {
        loginId: loginId.value,
        password: password.value,
        name: name.value,
        phoneNumber: phoneNumber.value,
        birthday: birthday.value,
        gender: gender.value
      };

      try {
        if (name.value === "") {
          alert("이름을 입력하세요.");
          return;
        }
        if (loginId.value === "") {
          alert("ID를 입력하세요.");
          return;
        }
        if (password.value === "") {
          alert("비밀번호를 입력하세요.");
          return;
        }
        if (password.value !== pwCheck.value) {
          alert("비밀번호 확인이 일치하지 않습니다.");
          return;
        }
        if (phoneNumber.value === "") {
          alert("전화번호를 입력하세요.");
          return;
        }
        if (birthday.value === "") {
          alert("생년월일을 선택하세요.");
          return;
        }
        if (gender.value === "") {
          alert("성별을 선택하세요.");
          return;
        }

        const config = {
          headers: {
            'Content-Type': 'application/json'  // JSON 형식으로 변경
          }
        }

        await axios.post("/user-service/signup", JSON.stringify(SignupData), config)
            .then((res) => {
              console.log(res);
              alert("회원가입 되었습니다.");
              navigateTo('/login');
            });

      } catch (err) {
        console.log(err);
      }
    };

    return {
      name,
      loginId,
      password,
      pwCheck,
      phoneNumber,
      birthday,
      gender,
      SignupSubmit,
    };
  },
};
</script>
