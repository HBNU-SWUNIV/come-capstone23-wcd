<template>
  <div class="HomePage">
    <div class="LoginScreen" v-if="$store.state.isLogin">
      모임 추천 기능 넣을것
      <router-view/>
    </div>
    <div class="NotLoginScreen" v-else>
      <img class="NotLoginImg" src="../assets/notLogin.png" />
      <label class="NeedLogin">로그인이 필요한 서비스입니다.</label>
      <div class="LoginLinkArea mt-4">
        <router-link class="LoginRouterlink" :to="{ name: 'LoginPage' }"
          >로그인</router-link
        >
        <router-link class="LoginRouterlink" style="margin-left: 80px;" :to="{ name: 'SignupPage' }"
          >회원가입</router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
export default {

};
</script>

<style>
.HomePage {
  height: 100%;
  width: 100%;
}
.NotLoginScreen {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.NotLoginImg {
  height: 500px;
  width: 500px;
  margin-top: 100px;
}
.NeedLogin {
  font-size: 30px;
  color: green;
}
.LoginLinkArea{
    display: flex;
}
</style>

<style scoped>
.LoginRouterlink {
  font-size: 45px;
  color: white;
  text-decoration: none;
}
.LoginRouterlink:hover {
color:purple
}
</style>