<template>
  <div class="item align-self-center">
    <form class="item align-self-center" @submit.prevent="fnLogin">
      <div class="logo mb-3" id="login_title">로그인</div>

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

      <div class="d-flex align-items-start mt-3">
        <div class="form-check">
          <input class="form-check-input" type="checkbox" id="autoLoginCheck" />

          <label class="form-check-label ml-2" id="flexCheckDefault">
            자동로그인
          </label>
        </div>

        <div class="ml-auto" id="find_pw">비밀번호 찾기</div>
      </div>

      <button class="btn btn-block text-center mt-3" id="login_btn">
        로그인
      </button>

      <div class="text-center pt-3 text-muted">
        회원이 아니신가요? &nbsp;
        <a href="/signup">회원가입</a>
      </div>
    </form>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      loginId: "",
      password: "",
    };
  },
  methods: {
    ...mapActions(["login"]), //vuex/actions에 있는 login 함수

    async fnLogin() {
      //async 함수로 변경
      if (this.loginId === "") {
        alert("ID를 입력하세요.");
        return;
      }

      if (this.password === "") {
        alert("비밀번호를 입력하세요.");
        return;
      }

      //로그인 API 호출
      try {
        let loginResult = true;
        // let loginResult = await this.login({
        //   loginId: this.loginId,
        //   password: this.password,
        // });
        if (loginResult) {
          alert("로그인 결과 : " + loginResult + "\n" + "로그인 되었습니다.");
          this.$router.push({
            path: "./",
          });
        }
      } catch (err) {
        if (err.message.indexOf("Network Error") > -1) {
          alert("서버에 접속할 수 없습니다. 상태를 확인해주세요.");
        } else {
          alert("로그인 정보를 확인할 수 없습니다.");
        }
      }
    },
    computed: {
      ...mapGetters({
        errorState: "getErrorState",
      }),
    },
  },
};
</script>

<style>
#login_title {
  color: white;
  text-align: center;
  font-size: 50px;
}
.form-check-input {
  transform: scale(1.5);
}
.form-check-label {
  font-size: 17px;
  color: #34da19;
}
#find_pw {
  color: #34da19;
}
#login_btn {
  width: 600px;
  height: 50px;
  font-size: 20px;
  color: white;
  background-color: #383838;
}
a {
  color: #34da19;
}
</style>