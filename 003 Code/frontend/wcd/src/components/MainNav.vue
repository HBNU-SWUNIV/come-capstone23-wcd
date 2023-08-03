<!-- 로그인 되어있고 /페이지면 네비게이션은 로그아웃만
    로그인 되어있고 /:id 페이지면 네비게이션은 홈, 게시판...,2D 버튼
  로그인 되어있지 않을때 채팅 네비게이션 없음 -->
<template>
  <div class="Nav-Wrap">
    <div class="NavShow" v-if="$store.state.isLogin">
      <div class="IsLogin-Params" v-if="this.$route.params.id">
        <router-link class="routerlink" :to="{ name: 'ClubHomePage' }"
          >홈</router-link
        >
        <router-link class="routerlink" :to="{ name: 'ClubNoticePage' }"
          >공지사항</router-link
        >
        <router-link class="routerlink" :to="{ name: 'ClubSchedulePage' }"
          >일정</router-link
        >
        <router-link class="routerlink" :to="{ name: 'ClubBoardPage' }"
          >게시판</router-link
        >
        <router-link class="routerlink" :to="{ name: 'ClubVideoCallPage' }"
          >화상채팅</router-link
        >
        <router-link class="routerlink" :to="{ name: 'ClubVirtualPage' }"
          >2D 가상환경</router-link
        >
      </div>
      <div class="IsLogin-Not-Params" v-else>
        <p
          class="LogOut-Btn"
          @mouseover="changeColor"
          @mouseout="resetColor"
          @click="logout"
          :style="{ color: textColor }"
        >
          로그아웃
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import store from "../vuex/store";
import router from '../router/index'

export default {
  setup() {
    // 로그아웃 마우스 이벤트
    const textColor = ref("white");

    const changeColor = () => {
      textColor.value = "green"; // 원하는 색으로 변경
    };

    const resetColor = () => {
      textColor.value = "white"; // 다시 원래 색으로 변경
    };

    const logout = () => {
      // localStorage.removeItem('isLogin');
      // store.state.isLogin = false;
      store.dispatch('logout')
      localStorage.removeItem('access_token');
      localStorage.removeItem('refresh_token');
      router.push({
        name:'HomePage'
      })
    };

    return {
      // 로그아웃 마우스 리턴값
      textColor,
      changeColor,
      resetColor,
      logout,
    };
  },
};
</script>

<style>
.Nav-Wrap {
  height: 80px;
  width: 1500px;
}
.IsLogin-Not-Params {
  height: 80px;
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: right;
}
.LogOut-Btn {
  cursor: pointer;
  font-size: 26px;
  text-align: center;
  margin-bottom: 0px;
  margin-right: 30px;
}
.IsLogin-Params {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.routerlink {
  height: 80px;
  font-size: 23px;
  margin-left: 55px;
  text-decoration: none;
  color: white;
  text-align: center;
  display: flex;
  align-items: center;
}
.router-link-exact-active {
  color: #34da19;
}
</style>