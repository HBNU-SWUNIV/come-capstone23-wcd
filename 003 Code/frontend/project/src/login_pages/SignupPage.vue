<template>
  <div class="SignupPage">
    <div class="SignupWrap">
      <div class="SignupTitle">회원가입</div>
      <form class="SignupForm" @submit.prevent="SignupSubmit">
        <div class="SignupBox">
          <input
            type="text"
            class="form-control"
            id="name"
            v-model="name"
            placeholder="이름"
          />

          <input
            type="text"
            class="form-control"
            id="loginId"
            placeholder="아이디"
            v-model="loginId"
          />

          <input
            type="password"
            class="form-control"
            id="password"
            placeholder="비밀번호"
            v-model="password"
          />

          <input
            type="password"
            class="form-control"
            id="pwdCheck"
            placeholder="비밀번호 확인"
            v-model="pwCheck"
          />

          <input
            type="tel"
            class="form-control"
            id="phoneNumber"
            v-model="phoneNumber"
            placeholder="전화번호"
          />

          <input
            type="date"
            class="form-control"
            id="birthday"
            v-model="birthday"
            placeholder="생년월일"
          />

          <div class="input-group mt-4">
            <div class="input-group-prepend">
              <label class="input-group-text" for="gender">성별</label>
            </div>
            <select class="custom-select" id="gender" v-model="gender">
              <option selected>선택...</option>
              <option value="남성">남성</option>
              <option value="여성">여성</option>
            </select>
          </div>
        </div>

        <div class="SignupSubmitBox">
          <button class="btn btn-block text-center mt-3" type="submit">
            회원가입
          </button>

          <div class="d-flex flex-row mt-3">
            <label class="question">이미 회원이신가요?</label>
            <router-link class="routerlink" :to="{ name: 'LoginPage' }"
              >로그인</router-link
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
      const SignupData = {
        name: name.value,
        loginId: loginId.value,
        password: password.value,
        phoneNumber: phoneNumber.value,
        birthday: birthday.value,
        gender: gender.value,
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
        await axios
          .post("http://localhost:3000/signup", JSON.stringify(SignupData))
          .then((res) => {
            console.log(res);
            alert("회원가입 되었습니다.");
            router.push({
              name: "LoginPage",
            });
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

<!-- <script>
import { reactive } from "vue";
import router from '../router/index'

export default {
  setup() {
    const signupData = reactive({
      name: "",
      loginId: "",
      password: "",
      phoneNumber: "",
      birthday: "",
      gender: "",
    });

    const SignupSubmit = async () => {
      try {
        
        const response = await fetch("/api/signup", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(signupData),
        });
        
        router.push({
          name: "LoginPage",
        });
      } catch (error) {
        console.error(error);
      }
    };

    return{
      signupData,
      SignupSubmit,
    }
  },
};
</script> -->

<style>
.SignupPage {
  display: flex;
  justify-content: center;
  height: 100%;
  width: 100%;
}
.SignupWrap {
  height: 400px;
  width: 1000px;
  margin-top: 100px;
}
.SignupTitle {
  font-size: 60px;
  text-align: center;
}
.SignupForm {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.SignupBox {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.SignupSubmitBox {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.form-control {
  height: 50px;
  width: 500px;
  margin-top: 30px;
}
.input-group-text {
  height: 50px;
}
.custom-select {
  height: 50px;
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
</style>

<style scoped>
.routerlink {
  font-size: 18px;
  color: #34da19;
  margin-left: 20px;
  text-decoration: none;
}
</style>