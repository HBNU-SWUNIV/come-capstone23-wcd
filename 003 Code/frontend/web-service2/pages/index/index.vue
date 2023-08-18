<template>
  <LeftNavDrawer/>
  <MainAppBar/>
<!--  <MainContent/>-->
  <router-view/>
  <RightNavDrawer/>
  <FooterBar/>
</template>

<script>
import MainAppBar from "@/components/MainAppBar.vue";
import LeftNavDrawer from "@/components/Main/LeftNavDrawer.vue";
import MainContent from "@/components/MainContent.vue";
import RightNavDrawer from "@/components/Main/RightNavDrawer.vue";
import FooterBar from "@/components/FooterBar.vue";

export default {
  components: {
    LeftNavDrawer,
    MainAppBar,
    MainContent,
    RightNavDrawer,
    FooterBar
  },
  setup() {

  }
}
</script>