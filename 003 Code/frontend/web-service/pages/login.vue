<template>
  <div style="width: 500px; margin-top: 180px;">
    <v-card style="background-color: rgba(0, 0, 0, 0.9)">
      <v-card-title class="d-flex justify-center">
        <h1 style="padding: 20px">LOGIN</h1>
      </v-card-title>
      <v-card-text>
        <v-form @submit.prevent="Login">
          <v-text-field
            v-model="email"
            label="E-mail"
            placeholder="E-mail"
            required
            type="E-mail"
          ></v-text-field>
          <v-text-field
            v-model="password"
            label="Password"
            placeholder="Password"
            required
            type="password"
          ></v-text-field>
          <v-checkbox v-model="remember" label="자동 로그인"></v-checkbox>
          <v-btn to="/signup">회원가입</v-btn>
          <v-btn type="submit" class="contrast">로그인</v-btn>
        </v-form>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: "",
      password: "",
      remember: false,
    };
  },
  layout(context) {
    return "login";
  },
  methods: {
    async Login() {
      // JSON 형식의 데이터
      let LoginData = {
        email: this.email,
        password: this.password,
      };

      try {
        if (this.email === "") {
          alert("이메일을 입력하세요.");
          return;
        }
        if (this.password === "") {
          alert("비밀번호를 입력하세요.");
          return;
        }

        const config = {
          headers: {
            "Content-Type": "application/json", // JSON 형식으로 변경
          },
        };

        await this.$axios
          .post("/user-service/login", JSON.stringify(LoginData), config)
          .then((res) => {
            console.log(res);
            alert("로그인 되었습니다.");
            // access_token을 in-memory 저장
            this.$store.commit("access_token", res.data.access_token);

            // refresh_token을 session에 저장
            sessionStorage.setItem("refresh_token", res.data.refresh_token);

            this.$router.push("/");
          });
      } catch (err) {
        console.log(err);
      }
    },
  },
};
</script>
