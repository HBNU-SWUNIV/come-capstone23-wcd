<template>
  <div class="LoginPage">
    <v-row class="LoginWrap justify-center align-center full-height" fluid style="height: 100vh;">
      <v-col cols="12" sm="8" md="4">
        <v-card class="border">
          <v-card-title class="text-center ma-5 text-h4">로 그 인</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="LoginSubmit">
              <v-text-field
                  label="아이디"
                  v-model="loginId"
              ></v-text-field>

              <v-text-field
                  label="비밀번호"
                  type="password"
                  v-model="password"
              ></v-text-field>
              <p class="error-message mb-4" v-if="errorMessage">{{ errorMessage }}</p>
              <v-btn class="mb-4" type="submit" block color="primary">로그인</v-btn>
              <div class="d-flex flex-row">
                <label class="question mr-1">회원이 아니신가요?</label>
                <nuxt-link to="/signup">회원가입</nuxt-link>
              </div>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import {ref} from "vue";
import axios from "axios";
import {useAuthStore} from "@/stores/auth";

export default {
  setup() {
    const store = useAuthStore() // `useAuthStore()`로 store 가져옴
    const loginId = ref("");
    const password = ref("");
    const errorMessage = ref("");

    const LoginSubmit = async () => {
      const loginData = {
        loginId: loginId.value,
        password: password.value
      };

      try {
        const config = {
          headers: {
            'Content-Type': 'application/json'
          }
        };

        const res = await axios.post(
            '/user-service/login',
            loginData,
            config
        );

        if (res.status === 200) {
          const {access_token, refresh_token, user_id} = res.data;
          store.setAccessToken(access_token);
          store.setRefreshToken(refresh_token);
          store.setUserId(user_id);

          navigateTo('/');
        }
      } catch (error) {
        errorMessage.value = "로그인에 실패하였습니다. 아이디 또는 비밀번호를 확인해주세요.";
      }
    };

    return {
      loginId,
      password,
      errorMessage,
      LoginSubmit
    };
  }
};
</script>

<style scoped>
.error-message {
  color: red;
}
</style>