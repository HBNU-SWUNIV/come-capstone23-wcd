<template>
  <div class="LoginPage">
    <v-row class="LoginWrap" justify="center" align="center">
      <v-col cols="12" sm="8" md="4">
        <v-card>
          <v-card-title class="justify-center">로 그 인</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="LoginSubmit">
              <v-text-field
                label="아이디"
                v-model="loginId"
                required
              ></v-text-field>

              <v-text-field
                label="비밀번호"
                type="password"
                v-model="password"
                required
              ></v-text-field>

              <v-btn type="submit" block color="primary">로그인</v-btn>

              <div class="d-flex flex-row mt-3">
                <label class="question">회원이 아니신가요?</label>
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
import { ref } from "vue";
import axios from "axios";

export default {
  setup() {
    const loginId = ref("");
    const password = ref("");

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
          'http://localhost:8000/user-service/login',
          loginData,
          config
        );

        if (res.status === 200) {
          const { access_token, refresh_token, user_id } = res.headers;
          localStorage.setItem('access_token', access_token);
          localStorage.setItem('refresh_token', refresh_token);
          localStorage.setItem('user_id', user_id);
          // Vuex와 라우터 사용
          this.$store.dispatch('login');
          this.$router.push({ name: 'HomePage' });
        } else {
          // 로그인 실패 시 처리
        }
      } catch (error) {
        console.error(error);
      }
    };

    return {
      loginId,
      password,
      LoginSubmit
    };
  }
};
</script>
