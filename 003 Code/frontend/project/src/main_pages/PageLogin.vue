<template>
  <div class="item align-self-center">
    <div class="item align-self-center">
      <div class="logo mb-3" id="login_title">로그인</div>

      <input type="text" class="form-control" id="ID" placeholder="아이디" />

      <input
        type="password"
        class="form-control"
        id="password"
        placeholder="비밀번호"
      />

      <div class="d-flex align-items-start mt-3">
        
        <div class="form-check">
          <input
            class="form-check-input"
            type="checkbox"
            id="autoLoginCheck"
          />

          <label class="form-check-label ml-2" id="flexCheckDefault">
            자동로그인
          </label>
        </div>

        <div class="ml-auto" id="find_pw">
          비밀번호 찾기
        </div>
      </div>

      <button class="btn btn-block text-center mt-3" id="login_btn">
        로그인
      </button>

      <div class="text-center pt-3 text-muted">
        회원이 아니신가요? &nbsp; 
        <a href="/signup">회원가입</a>
      </div>

    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
#login_title {
  color: white;
  text-align: center;
  font-size: 50px;
}
.form-check-input {
  transform : scale(1.5);
}
.form-check-label {
  font-size: 17px;
  color: #34DA19;
}
#find_pw {
  color: #34DA19
}
#login_btn {
  width: 600px;
  height: 50px;
  font-size: 20px;
  color: white;
  background-color: #383838;
}
a {
  color:#34DA19;
}
</style>