<!-- PageHeader.vue -->
<template>
  <div>
    <header>
      <div id="nav">
        <div style="float:left">
          <router-link to="/">홈</router-link> |
          <router-link to="/notice">공지사항</router-link> |
          <router-link to="/schedule">일정</router-link> |
          <router-link to="/board">게시판</router-link> |
          <router-link to="/cam">화상회의</router-link>
        </div>
        <div style="float:right">
          <router-link to="/signup">회원가입</router-link> |
          <router-link to="/login">로그인</router-link>
        </div>
      </div>
    </header>
    <hr />
  </div>
</template>
  
  <script>
export default {};
</script>
  
  <style scoped>
</style>