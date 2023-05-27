<template>
  <div class="nav">
        <div>
          <router-link to="/" style="margin-left: 60px">홈</router-link>
          <router-link to="/notice" style="margin-left: 60px">공지사항</router-link>
          <router-link to="/schedule" style="margin-left: 60px">일정</router-link>
          <router-link to="/board" style="margin-left: 60px">게시판</router-link>
          <router-link to="/cam" style="margin-left: 60px">화상회의</router-link>
        </div>
        
        <div>
          <router-link to="/login" style="margin-left: 650px">로그인</router-link>
          <router-link to="/signup" style="margin-left: 60px">회원가입</router-link>
        </div>
        
      </div>
</template>

<script>

</script>

<style>
.nav{
  height: 80px;
  width: 1500px;
  background-color: #383838;
  color: white;
  display: flex;
  align-items: center;
}

#login_button{
  height: 50px;
  width: 100px;
  border: none;
  background-color: #383838;
  color: white;
  font: bold;
  font-size: 20px;
}

</style>