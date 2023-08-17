<template>
  <div class="Wrap">
    <div class="d-flex flex-row">
      <MyClubNav />
      <div class="d-flex flex-column">
        <MainNav />
        <MainScreen />
      </div>
      <ChatNav />
    </div>
  </div>
</template>

<script>
import ChatNav from "./components/ChatNav.vue";
import MainScreen from "./components/MainScreen.vue";
import MyClubNav from "./components/MyClubNav.vue";
import MainNav from "./components/MainNav.vue";

export default {
  components: {
    ChatNav,
    MainScreen,
    MyClubNav,
    MainNav,
  },
  setup(){
    const loginState = localStorage.getItem('isLogin')
    console.log(loginState)
  }
};
</script>

<style>
.Wrap {
  height: 1080px;
  width: 1920px;
  background-color: #383838;
}
</style>