<template>
  <div class="LoginPage">
    <div class="LoginWrap">
      <div class="LoginTitle">로 그 인</div>
      <form class="LoginForm" @submit.prevent="LoginSubmit">
        <div class="LoginBox">
          <input
            type="text"
            class="form-control"
            placeholder="아이디"
            v-model="loginId"
            required
          />

          <input
            type="password"
            class="form-control"
            placeholder="비밀번호"
            v-model="password"
            required
          />
        </div>

        <div class="LoginSubmitBox">
          <button class="btn btn-block text-center mt-3" type="submit">
            로그인
          </button>

          <div class="d-flex flex-row mt-3">
            <label class="question">회원이 아니신가요?</label>
            <router-link class="router" :to="{ name: 'SignupPage' }"
              >회원가입</router-link
            >
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import axios from "axios";
import router from "../router/index";
import store from "@/vuex/store";

export default{
  setup(){
    const loginId = ref("");
    const password = ref("");

    const LoginSubmit = async () => {
      const loginData = {
        loginId: loginId.value,
        password: password.value
      };

      try{
        const config = {
          headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'text/plain'
          }
        }

        await axios.post('http://localhost:8000/user-service/login', loginData, config)
            .then((res) => {
              if (res.status === 200) {
                // 모든 헤더 이름은 소문자
                let accessToken = res.headers["access_token"]; // 응답헤더에서 토큰 받기
                let refreshToken = res.headers['refresh_token']; // 응답헤더에서 토큰 받기
                localStorage.setItem('access_token', accessToken);
                localStorage.setItem('refresh_token', refreshToken);
                store.dispatch('login')
                router.push({
                  name: 'HomePage'
                });
              } else {
                // 로그인 실패 시 구현 해야함
              }
            })
      } catch(error){
        console.error(error)
      }
    }
    return{
      loginId,
      password,
      LoginSubmit,
    }
  }
}
</script>


<style>
.LoginPage {
  display: flex;
  justify-content: center;
  height: 100%;
  width: 100%;
}
.LoginWrap {
  height: 400px;
  width: 1000px;
  margin-top: auto;
  margin-bottom: auto;
}
.LoginTitle {
  font-size: 60px;
  text-align: center;
}
.LoginForm {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.LoginBox {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.LoginSubmitBox {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.form-control {
  height: 50px;
  width: 500px;
  margin-top: 30px;
}
.btn {
  width: 500px;
  height: 50px;
  font-size: 23px;
  background-color: #383838;
  color: white;
}
a {
  color: #34da19;
}
.question {
  font-size: 20px;
}
.router {
  font-size: 18px;
  color: #34da19;
  margin-left: 20px;
  text-decoration: none;
  font-family: "font";
}
</style>

<style scoped>
</style>