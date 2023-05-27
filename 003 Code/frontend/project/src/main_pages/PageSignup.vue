<template>
  <div class="d-flex justify-content-center">
    <div>
      <div class="logo mb-3" style="margin-top: 45px;">
        <div class="col-md-12 text-center">
          <h1>회원가입</h1>
        </div>
      </div>
      <input
        type="text"
        class="form-control"
        placeholder="이름"
      />

      <input
        type="text"
        class="form-control"
        placeholder="아이디"
      />

      <input
        type="password"
        class="form-control"
        id="password"
        placeholder="비밀번호"
      />

      <input
        type="password"
        class="form-control"
        id="password_check"
        placeholder="비밀번호 확인"
      />

      <input
        type="tel"
        class="form-control"
        placeholder="전화번호"
      />

      <input
        type="date"
        class="form-control"
        placeholder="생년월일"
      />

      <div class="input-group mb-3 mt-5">
        <div class="input-group-prepend">
          <label class="input-group-text" for="inputGroupSelect01">성별</label>
        </div>
        <select class="custom-select" id="inputGroupSelect01">
          <option selected>선택...</option>
          <option value="1">남성</option>
          <option value="2">여성</option>
        </select>
      </div>

      <button class="btn btn-block text-center my-5" id="signup_btn">
        회원가입
      </button>
    </div>
  </div>
</template>

<script>
</script>

<style>
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
}
</style>