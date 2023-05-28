<template>
  <div id="full-screen">
    <div id="left-screen">
      <div id="app-name">W &nbsp; C &nbsp; D</div>
      <img id="user-profile-img" src="./assets/basicProfile.jpeg" alt="Logo" />
    </div>
    <div id="center-screen">
      <div id="nav">
        <div>
          <button class="button" style="margin-left: 60px">홈</button>
          <button class="button">공지사항</button>
          <button class="button">일정</button>
          <button class="button">게시판</button>
          <button class="button">화상회의</button>
        </div>
        
        <div>
          <button class="button-id" style="margin-left: 500px">로그인</button>
          <button class="button-id">회원가입</button>
        </div>
        
      </div>
      <div id="main-screen">
        <div class="club-profile">
          <img id="club-profile" src="./assets/clublogo.png" alt="ClubProfile">
        </div>
      </div>
    </div>
    <div id="right-screen">
      <div class="space">이 공간은 여백</div>
      <div class="chat_nav">채팅 네비게이션</div>
      <div class="chat_room"></div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
#full-screen{
  height: 1080px;
  width: 1920px;
}
#left-screen{
  height: 1080px;
  width: 120px;
  background-color: #383838;
  float: left;
}
#right-screen{
  height: 1080px;
  width: 300px;
  background-color: #383838;
  float: left;
}
#center-screen{
  height: 1080px;
  width: 1500px;
 float: left;
}
#nav{
  height: 80px;
  width: 1500px;
  background-color: #383838;
  color: white;
  display: flex;
  align-items: center;
}
#main-screen{
  height: 1000px;
  width: 1500px;
  background-color: #232324;
}
.button{
  height: 30px;
  width: 130px;
  border: none;
  background-color: #383838;
  color: white;
  font: bold;
  font-size: 22px;
}
.button-id{
  height: 50px;
  width: 100px;
  border: none;
  background-color: #383838;
  color: white;
  font: bold;
  font-size: 20px;
}
#user-profile-img {
 max-width: 70px;
 max-height: 70px;
 margin-left: 25px;
 margin-top: 5px;
 border-radius: 20%;
}
#app-name{
  width: 60px;
  height: 30px;
  text-align: center;
  color: white;
  font-size: 14px;
  margin-left: 30px;
  margin-top: 15px;
}
.club-profile{
  height: 225px;
  width: 400px;
}
#club-profile{
  max-width: 400px;
  max-height: 225px;
  margin-left: 60px;
  margin-top: 50px;
  border-radius: 15%;
}
.space{
  height: 80px;
  width: 300px;
}
.chat_nav{
  height: 50px;
  width: 300px;
}
.chat_room{
  height: 950px;
  width: 300px;
}
</style>



<!-- <template>
   <div class="container">
    <div class="input-form-backgroud row">
      <div class="input-form col-md-12 mx-auto">
        <h4 class="mb-3 pt-3">회원가입</h4>
        <form class="validation-form" novalidate>
          <div class="row">
            <div class="col-md-6 mb-3">
              <label for="name">이름</label>
              <input type="text" class="form-control" id="name" placeholder="" value="" required>
              <div class="invalid-feedback">
                이름을 입력해주세요.
              </div>
            </div>
            <div class="col-md-6 mb-3">
              <label for="nickname">아이디</label>
              <input type="text" class="form-control" id="nickname" placeholder="" value="" required>
              <div class="invalid-feedback">
                별명을 입력해주세요.
              </div>
            </div>
          </div>

          <div class="mb-3">
            <label for="password">비밀번호</label>
            <input type="password" class="form-control" id="password" placeholder="" required>
            <div class="invalid-feedback">
              비밀번호를 입력해주세요.
            </div>
            <label for="check_password">비밀번호 확인</label>
            <input type="password" class="form-control" id="check_password" placeholder="" required>
            <div class="invalid-feedback">
              비밀번호를 다시 입력해주세요.
            </div>
          </div>

          <div class="mb-3">
            <label for="phone_number">전화번호</label>
            <input type="phone_number" class="form-control" id="phone_number" placeholder="" required>
            <div class="invalid-feedback">
              전화번호를 입력해주세요.
            </div>
          </div>

          <form class="validation-form" novalidate>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="birthday">생년월일</label>
                <input type="text" class="form-control" id="birthday" placeholder="" value="" required>
              <div class="invalid-feedback">
                생년월일을 입력해주세요.
              </div>
            </div>
            <div class="col-md-6 mb-3">
              <label for="gender">성별</label>
              <div class="input-group mb-3">
                <select class="custom-select" id="inputGroupSelect02">
                  <option selected>성별을 선택해주세요.</option>
                  <option value="male">남성</option>
                  <option value="female">여성</option>
                </select>
              <div class="input-group-append">
              <label class="input-group-text" for="inputGroupSelect02">성별</label>
            </div>
          </div>
              <div class="invalid-feedback">
                성별을 선택해주세요.
              </div>
            </div>
            </div>
          </form>

          <div class="mb-4"></div>
          <button class="btn btn-primary btn-lg btn-block" type="submit">가입 완료</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {


}
</script>

<style>

</style> -->