<template>
  <div class="NavBar">
    <div class="NavList">
      <div v-if="$store.state.isLogin">
        <router-link class="routerlink" :to="{ name: 'PageHome' }"
          >홈</router-link
        >
        <router-link class="routerlink" :to="{ name: 'NoticePage' }"
          >공지사항</router-link
        >
        <router-link class="routerlink" :to="{ name: 'SchedulePage' }"
          >일정</router-link
        >
        <router-link class="routerlink" :to="{ name: 'BoardPage' }"
          >게시판</router-link
        >
        <router-link class="routerlink" :to="{ name: 'CamPage' }"
          >화상회의</router-link
        >
        <router-link class="routerlink" :to="{ name: 'VirtualPage' }"
          >2D가상환경</router-link
        >
      </div>
    </div>
    <div class="LoginList">
      <router-link
        class="LoginRouterlink mr-5"
        :to="{ name: 'HomePage' }"
        v-if="$store.state.isLogin"
        @click="logout"
        >로그아웃</router-link
      >
      <div v-else>
        <router-link class="LoginRouterlink" :to="{ name: 'LoginPage' }"
          >로그인</router-link
        >
        <router-link class="LoginRouterlink mr-5" :to="{ name: 'SignupPage' }"
          >회원가입</router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
import store from "@/vuex/store";

export default {
  setup() {
    const logout = () => {
      store.state.isLogin = false;
    };

    return {
      logout,
    };
  },
};
</script>

<style>
.NavBar {
  display: flex;
  height: 80px;
  width: 100%;
  justify-content: space-between;
}
.NavList {
  display: flex;
  align-items: center;
}
.LoginList {
  display: flex;
  align-items: center;
}
</style>

<style scoped>
.routerlink {
  font-size: 23px;
  margin-left: 55px;
  text-decoration: none;
  color: white;
}
.LoginRouterlink {
  font-size: 20px;
  margin-left: 40px;
  text-decoration: none;
  color: white;
}
.router-link-exact-active {
  color: #34da19;
}
</style>