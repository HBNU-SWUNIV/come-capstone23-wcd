<template>
  <div class="item align-self-center">
    <form class="item align-self-center" id="signup_form" @submit.prevent="onSubmit">
      <div class="logo mb-3" id="signup_title">회원가입</div>

      <input
        type="text"
        class="form-control"
        id="userName"
        v-model="userName"
        placeholder="이름"
      />

      <input
        type="text"
        class="form-control"
        id="loginId"
        v-model="loginId"
        placeholder="아이디"
      />

      <input
        type="password"
        class="form-control"
        id="password"
        v-model="password"
        placeholder="비밀번호"
      />

      <input
        type="password"
        class="form-control"
        placeholder="비밀번호 확인"
        v-model="password_confirmation"
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

      <div class="input-group mb-3 mt-5">
        <div class="input-group-prepend">
          <label class="input-group-text" for="gender">성별</label>
        </div>
        <select class="custom-select" id="gender" v-model="gender">
          <option selected>선택...</option>
          <option value="남성">남성</option>
          <option value="여성">여성</option>
        </select>
      </div>

      <button
        type="submit"
        class="btn btn-block text-center my-5"
        id="signup_btn"
        @click="postData"
      >
        회원가입
      </button>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import { useRouter } from "vue-router";

const data = () => {
  const userName = document.getElementById("userName").value;
  const loginId = document.getElementById("loginId").value;
  const password = document.getElementById("password").value;
  const phoneNumber = document.getElementById("phoneNumber").value;
  const birthday = document.getElementById("birthday").value;
  const gender = document.getElementById("gender").value;

  return {
    userName: userName,
    loginId: loginId,
    password: password,
    phoneNumber: phoneNumber,
    birthday: birthday,
    gender: gender,
  };
};

export default {
  setup() {
    const router = useRouter();
    
    const postData = () => {
      axios
        .post("http://localhost:3000/users", data())
        .then((res) => {
          console.log(res);
        })
        .then((err) => {
          console.log(err);
        });
        router.push({
            path: "/login",
          });
    };

    return {
      postData,
    };
  },
};
</script>

<style>
#signup_title {
  color: white;
  text-align: center;
  font-size: 50px;
}
.form-control {
  font-size: 22px;
  border: none;
  width: 600px;
  height: 50px;
  margin-top: 40px;
}
.input-group {
  width: 600px;
  height: 50px;
}
.custom-select {
  width: 600px;
  height: 50px;
}
.input-group-text {
  font-size: 20px;
}
.custom-select {
  font-size: 20px;
}
#signup_btn {
  width: 600px;
  height: 50px;
  font-size: 20px;
  color: white;
  background-color: #383838;
}
.black-bg {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  position: fixed;
}
.white-bg {
  width: 90%;
  margin: 80px auto;
  background: white;
  border-radius: 5px;
  padding: 20px 0;
}
.close {
  cursor: pointer;
  border: none;
  background: #6667ab;
  color: white;
  font-weight: bold;
  border-radius: 5px;
  padding: 5px 15px;
}
.close:hover {
  color: white;
  font-weight: bold;
  transform: scale(1.1);
  transition: all 0.5s;
}
</style>