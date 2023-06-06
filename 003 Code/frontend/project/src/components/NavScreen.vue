<template>
  <div class="" id="nav_screen">
    <router-link to="/">홈</router-link>
    <router-link to="/notice">공지사항</router-link>
    <router-link to="/schedule">일정</router-link>
    <router-link to="/board">게시판</router-link>
    <router-link to="/cam">화상회의</router-link>
    <router-link to="/login" style="margin-left: 600px;">로그인</router-link>
    <router-link to="/signup">회원가입</router-link>
  </div>
</template>

<script>

</script>

<style scoped>
#nav_screen{
  position: absolute;
  display: flex;
  align-items: center;
  width: 1500px;
  height: 80px;
  left: 120px;
  top: 0px;
  background-color: #383838;
  padding: 30px;
}

#nav_screen a{
  width: 130px;
  height: 30px;
  font-size: 20px;
  text-align: center;
  color: white;
}
#nav_screen a.router-link-exact-active {
  color: #34DA19;
}
</style>