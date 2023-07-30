<template>
    <div class="HomeNavBar">
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
  .HomeNavBar {
    display: flex;
    height: 80px;
    width: 100%;
    justify-content: right;
  }
  .LoginList {
    display: flex;
    align-items: center;
  }
  </style>
  
  <style scoped>
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