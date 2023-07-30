<template>
  <div class="BasicDisplay">
    <div class="BasicWrap">
      <MyClub />
      <div class="MiddleWrap">
        <HomeNavBar v-if="!hasParams" />
        <NavBar v-else/>
        <MainScreen />
      </div>
      <ChatNav />
    </div>
  </div>
</template>

<script>
import ChatNav from "./components/ChatNav.vue";
import MainScreen from "./components/MainScreen.vue";
import MyClub from "./components/MyClub.vue";
import HomeNavBar from "./components/HomeNavBar.vue";
import NavBar from './components/NavBar.vue'
import { computed } from "vue";
import { useRoute } from "vue-router";

export default {
  components: {
    MyClub,
    HomeNavBar,
    MainScreen,
    ChatNav,
    NavBar,
  },
  setup() {
    const route = useRoute();

    const hasParams = computed(() => {
      return Object.keys(route.params).length > 0;
    });

    return {
      hasParams,
    };
  },
};
</script>

<style>
.BasicDisplay {
  height: 1080px;
  width: 1920px;
}
.BasicWrap {
  display: flex;
  height: 1080px;
  width: 1920px;
  background-color: #383838;
}
.MiddleWrap {
  height: 100%;
  width: 1500px;
}
.routerlink:hover {
  color: purple;
}
.LoginRouterlink:hover {
  color: purple;
}
</style>