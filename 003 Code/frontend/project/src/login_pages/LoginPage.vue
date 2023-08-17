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
            <router-link class="routerlink" :to="{ name: 'SignupPage' }"
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

export default {
  setup() {
    const loginId = ref("");
    const password = ref("");

    const LoginSubmit = async () => {
      const loginData = {
        loginId: loginId.value,
        password: password.value,
      };
      try {
        if (loginId.value === "") {
          alert("ID를 입력하세요.");
          return;
        }
        if (password.value === "") {
          alert("비밀번호를 입력하세요.");
          return;
        }
        await axios
          .post("http://localhost:3000/login", JSON.stringify(loginData))
          .then((res) => {
            console.log(res);
            alert("로그인 되었습니다.");
            store.commit("changeLoginState");

            router.push({
              name: "HomePage",
            });
          });
      } catch (err) {
        alert("다시 시도해주세요.");
        console.log(err);
      }
    };

    return {
      loginId,
      password,
      LoginSubmit,
    };
  },
};
</script>

<!-- <script>
import {reactive} from 'vue'
import router from "../router/index";

export default{
  setup(){
    const loginData = reactive({
      loginId:'',
      password:'',
    })

    const LoginSubmit = async () => {
      try{
        
        const response = await fetch('/api/login',{
          method:'POST',
          header:{
            'Content-Type':'application/json',
          },
          body:JSON.stringify(loginData),
        });
        
        const {access_token, refresh_token} = await response.json();
        
        localStorage.setItem('access_token', access_token);
        localStorage.setItem('refresh_token', refresh_token);
        router.push({
          name:'HomePage'
        })
      } catch(error){
        console.error(error)
      }
    }
    return{
      loginData,
      LoginSubmit,
    }
  }
}
</script> -->


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
}
a {
  color: #34da19;
}
.question {
  font-size: 20px;
}
</style>

<style scoped>
.routerlink {
  font-size: 18px;
  color: #34da19;
  margin-left: 20px;
  text-decoration: none;
  font-family: "font";
}
</style>